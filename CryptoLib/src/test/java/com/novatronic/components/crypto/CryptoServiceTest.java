/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.support.ResourceHelper;
import org.apache.log4j.Logger;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ricardo
 */
public class CryptoServiceTest {

    private static final Logger LOGGER = Logger.getLogger(CryptoServiceTest.class);
    CryptoService instance;

    public ExpectedException expectedException = ExpectedException.none();

    public CryptoServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new CryptoService(ResourceHelper.findAsProperties("CipherService.properties"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class CryptoService.
     */
    @Test
    public void testProcessEncription() {
        System.out.println("process");
        CryptoDTO request;

        request = new CryptoDTO(CryptoType.SYMETRIC, "E", "AES", "Hola Mundo".getBytes());

        CryptoResponseDTO result = instance.process(request);
        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("00"));
    }

    @Test
    public void testProcessDecription() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        byte[] dataAEncriptar;

        dataAEncriptar = "Hola Mundo".getBytes();

        request = new CryptoDTO(CryptoType.SYMETRIC, "E", "AES", dataAEncriptar);

        result = instance.process(request);

        request = new CryptoDTO(CryptoType.SYMETRIC, "D", "AES", result.getDataRespuesta());
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("00"));
        assertArrayEquals(dataAEncriptar, result.getDataRespuesta());
    }

    @Test
    public void testProcessSign() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        byte[] dataACifrar;

        dataACifrar = "Hola Mundo".getBytes();

        request = new CryptoDTO(CryptoType.ASYMETRIC, "F", "SHA1withRSA", dataACifrar);
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("00"));

    }

    @Test
    public void testProcessVerify() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        byte[] dataACifrar;

        dataACifrar = "Hola Mundo".getBytes();

        request = new CryptoDTO(CryptoType.ASYMETRIC, "F", "SHA1withRSA", dataACifrar);
        result = instance.process(request);

        request = new CryptoDTO(CryptoType.ASYMETRIC, "V", "SHA1withRSA", dataACifrar);
        request.setDataToVerified(result.getDataRespuesta());
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("00"));
    }

    @Test
    public void testAlgoritmoInvalido() {
        System.out.println("process");
        CryptoDTO request;

        request = new CryptoDTO(CryptoType.SYMETRIC, "E", "ABC", "A".getBytes());

        CryptoResponseDTO result = instance.process(request);
        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("01"));
        assertTrue(result.getDescripcionRespuesta().startsWith("No se permite este algoritmo para "));
    }

    @Test
    public void testOperacionInvalida() {
        System.out.println("process");
        CryptoDTO request;

        request = new CryptoDTO(CryptoType.SYMETRIC, "F", "AES", "A".getBytes());

        CryptoResponseDTO result = instance.process(request);
        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("02"));
        assertTrue(result.getDescripcionRespuesta().startsWith("No se permite esta operacion para "));
    }

    @Test
    public void testMensajeNoExiste() {
        System.out.println("process");
        CryptoDTO request;

        request = new CryptoDTO(CryptoType.SYMETRIC, "E", "AES", null);

        CryptoResponseDTO result = instance.process(request);
        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("03"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Contenido del mensaje no existe"));

        request.setData(new byte[0]);

        result = instance.process(request);
        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("03"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Contenido del mensaje no existe"));
    }

    @Test
    public void testConfiguracionInvalidaCifrar() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.SYMETRIC, "E", "AES", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalid.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("04"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("No se tienen suficientes valores para la operacion"));
    }

    @Test
    public void testConfiguracionInvalidaDesCifrar() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.SYMETRIC, "D", "AES", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalid.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("04"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("No se tienen suficientes valores para la operacion"));
    }

    @Test
    public void testConfiguracionInvalidaFirmar() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.ASYMETRIC, "F", "SHA1withRSA", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalid.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("04"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("No se tienen suficientes valores para la operacion"));
    }

    @Test
    public void testConfiguracionInvalidaVerificar() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.ASYMETRIC, "V", "SHA1withRSA", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalid.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("04"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("No se tienen suficientes valores para la operacion"));
    }

    @Test
    public void testNoExisteLlaveSimetrica() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.SYMETRIC, "D", "AES", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalidKey.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("05"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al obtener llave simetrica"));
    }

    @Test
    public void testNoExisteLlavePrivada() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.ASYMETRIC, "F", "SHA1withRSA", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalidKey.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("06"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al obtener llave privada"));
    }

    @Test
    public void testNoExisteLlavePublica() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.ASYMETRIC, "V", "SHA1withRSA", "A".getBytes());

        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigInvalidKey.properties"));

        result = instance.process(request);

        assertTrue(result.getCodigoRespuesta().equals("07"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al obtener llave publica"));
    }

    @Test
    public void testErrorCifrarAlgoritmo() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        request = new CryptoDTO(CryptoType.SYMETRIC, "E", "DES", "Hola Mundo".getBytes());

        result = instance.process(request);
        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("08"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al cifrar el mensaje"));
    }

    @Test
    public void testErrorDesCifrarAlgoritmo() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;

        request = new CryptoDTO(CryptoType.SYMETRIC, "D", "DES", "Hola Mundo".getBytes());
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("09"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al descifrar el mensaje"));
    }

    @Test
    public void testErrorFirmarAlgoritmo() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        byte[] dataACifrar;

        dataACifrar = "Hola Mundo".getBytes();

        request = new CryptoDTO(CryptoType.ASYMETRIC, "F", "RIPEMD256withRSA", dataACifrar);
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("10"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al firmar el mensaje"));
    }

    @Test
    public void testErrorVerificarFirmaAlgoritmo() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        byte[] dataACifrar;

        dataACifrar = "Hola Mundo".getBytes();

        request = new CryptoDTO(CryptoType.ASYMETRIC, "V", "RIPEMD256withRSA", dataACifrar);
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("11"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error al verificar la firma del mensaje"));
    }
    
    @Test
    public void testErrorSistema() {
        System.out.println("process");
        CryptoDTO request;
        CryptoResponseDTO result;
        byte[] dataACifrar;

        dataACifrar = "Hola Mundo".getBytes();

        request = new CryptoDTO(CryptoType.ASYMETRIC, "V", "RIPEMD256withRSA", dataACifrar);
        CryptoService instance = new CryptoService(ResourceHelper.findAsProperties("ConfigNoExisteImpl.properties"));
        result = instance.process(request);

        LOGGER.debug("resultado=" + result);

        assertTrue(result.getCodigoRespuesta().equals("99"));
        assertTrue(result.getDescripcionRespuesta().equalsIgnoreCase("Error en el sistema"));
    }
}
