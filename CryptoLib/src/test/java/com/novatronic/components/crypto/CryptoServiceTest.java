/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

import com.novatronic.components.support.CipherType;
import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ricardo
 */
public class CryptoServiceTest {
    
    private static final Logger LOGGER = Logger.getLogger(CryptoServiceTest.class);
    
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
        CryptoService instance = new CryptoService();
        
        request = new CryptoDTO(CipherType.SYMETRIC, "E", "AES", "Hola Mundo".getBytes());
        
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
        CryptoService instance = new CryptoService();
        
        dataAEncriptar = "Hola Mundo".getBytes();
        
        request = new CryptoDTO(CipherType.SYMETRIC, "E", "AES", dataAEncriptar);
        
        result = instance.process(request);
        
        request = new CryptoDTO(CipherType.SYMETRIC, "D", "AES", result.getDataRespuesta());
        result = instance.process(request);
        
        LOGGER.debug("resultado=" + result);
        
        assertTrue(result.getCodigoRespuesta().equals("00"));
        assertArrayEquals(dataAEncriptar , result.getDataRespuesta());
    }
    
}
