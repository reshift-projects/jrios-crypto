package com.novatronic.cripto.type;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.novatronic.cripto.controller.CriptoFactory;
import com.novatronic.cripto.resource.ResourceHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import com.novatronic.cripto.controller.Cripto;

public class ByteCriptoTest {

    private static final Logger log = Logger.getLogger(ByteCriptoTest.class);

    @Ignore
    @Test
    public void encryptBytes() {
        try {
            log.debug("==========================ENCRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Cripto crypt = CriptoFactory.getInstance(prop);
            Path path = Paths.get("E:/Josue/POCCrypt/WFConector.xml");
            byte[] in = Files.readAllBytes(path);

            byte[] outExpected = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConectorEnc.xml"));
            byte[] out = (byte[]) crypt.encrypt(in);

            assertArrayEquals(outExpected, out);
        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void encryptBytesWithDES() throws Exception {
        log.debug("==========================ENCRYPT TEST=========================");
        Properties prop;
        prop = ResourceHelper.findAsProperties("CipherOptions_Bytes_DES.properties");

        Cripto crypt = CriptoFactory.getInstance(prop);
        Path path = Paths.get("E:/Josue/POCCrypt/WFConector.xml");
        byte[] in = Files.readAllBytes(path);

        byte[] outExpected = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConectorEncDES.xml"));
        byte[] out = (byte[]) crypt.encrypt(in);

        assertArrayEquals(outExpected, out);
    }

    @Ignore
    @Test
    public void decryptBytes() {
        try {
            log.debug("==========================DECRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Cripto crypt = CriptoFactory.getInstance(prop);

            Path path = Paths.get("E:/Josue/POCCrypt/WFConectorEnc.xml");
            byte[] in = Files.readAllBytes(path);

            byte[] outExpected = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConectorDec.xml"));

            byte[] out = (byte[]) crypt.decrypt(in);

            assertArrayEquals(outExpected, out);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void decryptBytesWithDES() throws Exception {
        log.debug("==========================DECRYPT TEST=========================");
        Properties prop;
        prop = ResourceHelper.findAsProperties("CipherOptions_Bytes_DES.properties");

        Cripto crypt = CriptoFactory.getInstance(prop);

        Path path = Paths.get("E:/Josue/POCCrypt/WFConectorEncDES.xml");
        byte[] in = Files.readAllBytes(path);

        byte[] outExpected = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConector.xml"));

        byte[] out = (byte[]) crypt.decrypt(in);

        assertArrayEquals(outExpected, out);
    }

    @Test
    public void signBytes() {
        try {
            log.debug("==========================SIGN TEST=========================");
            Properties prop;
            boolean result;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Cripto crypt = CriptoFactory.getInstance(prop);
            byte[] in = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConector.xml"));

            byte[] outExpected = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConector.crt"));

            byte[] out = (byte[]) crypt.sign(in);

            assertArrayEquals(outExpected, out);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void signBytesWithDES() throws IOException {
        log.debug("==========================SIGN TEST=========================");
        Properties prop;
        prop = ResourceHelper.findAsProperties("CipherOptions_Bytes_DES.properties");

        Cripto crypt = CriptoFactory.getInstance(prop);
        byte[] in = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConector.xml"));

        byte[] outExpected = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConector.crt"));

        byte[] out = (byte[]) crypt.sign(in);

        assertArrayEquals(outExpected, out);
    }

    @Test
    public void verifyBytes() {
        try {
            log.debug("==========================VERIFY TEST=========================");
            Properties prop;
            boolean result;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Cripto crypt = CriptoFactory.getInstance(prop);

            byte[] in = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConectorDec.xml"));
            byte[] out = Files.readAllBytes(Paths.get("E:/Josue/POCCrypt/WFConector.crt"));
            result = crypt.verify(in, out);

            log.info("Es valido?: " + result);
            Assert.assertTrue(result);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

}
