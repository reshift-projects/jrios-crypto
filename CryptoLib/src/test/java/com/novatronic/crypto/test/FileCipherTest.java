package com.novatronic.crypto.test;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.novatronic.components.crypto.Crypto;
import com.novatronic.components.crypto.CryptoFactory;
import com.novatronic.components.support.ResourceHelper;
import static org.junit.Assert.*;

public class FileCipherTest {

    private static final Logger log = Logger.getLogger(FileCipherTest.class);

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
    public void encryptDES() {
            log.debug("==========================ENCRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions_DES.properties");

            Crypto crypt = CryptoFactory.getInstance(prop);

            crypt.encrypt(null);
 
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

            result = crypt.verify(null,null);

            log.info("Es valido?: " + result);
            Assert.assertEquals(true, result);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }
}
