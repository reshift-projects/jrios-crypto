package com.novatronic.crypto.test;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.novatronic.components.crypto.Crypto;
import com.novatronic.components.crypto.CryptoFactory;
import com.novatronic.components.support.ResourceHelper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;

public class CryptoTest {

    private static final Logger log = Logger.getLogger(CryptoTest.class);

    @Test
    public void encrypt() {
        try {
            log.debug("==========================ENCRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
            
            crypt.encrypt(null);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void decrypt() {
        try {
            log.debug("==========================DECRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
            crypt.decrypt(null);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void sign() {
        try {
            log.debug("==========================SIGN TEST=========================");
            Properties prop;
            boolean result;
            prop = ResourceHelper.findAsProperties("CipherOptions.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
            result = (Boolean) crypt.sign(null);

            log.info("Es valido?: " + result);
            Assert.assertEquals(true, result);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void verify() {
        try {
            log.debug("==========================VERIFY TEST=========================");
            Properties prop;
            boolean result;
            prop = ResourceHelper.findAsProperties("CipherOptions.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
            
            result = (Boolean) crypt.sign(null);
            
            result = crypt.verify(null);

            log.info("Es valido?: " + result);
            Assert.assertEquals(true, result);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void encryptBytes() {
        try {
            log.debug("==========================ENCRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
            Path path = Paths.get("D:/POCCrypt/WFConector.xml");
            byte[] in = Files.readAllBytes(path);
            
            byte[] outExpected = Files.readAllBytes(Paths.get("D:/POCCrypt/WFConectorEnc.xml"));
            byte[] out = (byte[]) crypt.encrypt(in);
            
            assertArrayEquals(outExpected, out);
        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void decryptBytes() {
        try {
            log.debug("==========================DECRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
            
            Path path = Paths.get("D:/POCCrypt/WFConectorEnc.xml");
            byte[] in = Files.readAllBytes(path);
            
            byte[] outExpected = Files.readAllBytes(Paths.get("D:/POCCrypt/WFConectorDec.xml"));
            
            byte[] out = (byte[]) crypt.decrypt(in);

            assertArrayEquals(outExpected, out);
            
        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

    @Test
    public void verifyBytes() {
        try {
            log.debug("==========================VERIFY TEST=========================");
            Properties prop;
            boolean result;
            prop = ResourceHelper.findAsProperties("CipherOptions_Bytes.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);
                
            byte[] in = Files.readAllBytes(Paths.get("D:/POCCrypt/WFConector.xml"));
            crypt.sign(in);
            
            
            in = Files.readAllBytes(Paths.get("D:/POCCrypt/WFConectorDec.xml"));
            result = crypt.verify(in);

            log.info("Es valido?: " + result);
            Assert.assertTrue(result);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }

}
