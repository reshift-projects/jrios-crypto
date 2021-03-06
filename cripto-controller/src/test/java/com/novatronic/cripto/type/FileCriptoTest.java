package com.novatronic.cripto.type;

import com.novatronic.cripto.controller.Cripto;
import com.novatronic.cripto.controller.CriptoFactory;
import com.novatronic.cripto.resource.ResourceHelper;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.fail;

public class FileCriptoTest {

    private static final Logger log = Logger.getLogger(FileCriptoTest.class);


    @Test
    public void encrypt() {
        try {
            log.debug("==========================ENCRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions.properties");

            Cripto crypt = CriptoFactory.getInstance(prop);

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

        Cripto crypt = CriptoFactory.getInstance(prop);

        crypt.encrypt(null);

    }


    @Test
    public void decrypt() {
        try {
            log.debug("==========================DECRYPT TEST=========================");
            Properties prop;
            prop = ResourceHelper.findAsProperties("CipherOptions.properties");

            Cripto crypt = CriptoFactory.getInstance(prop);
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

            Cripto crypt = CriptoFactory.getInstance(prop);
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

            Cripto crypt = CriptoFactory.getInstance(prop);

            result = (Boolean) crypt.sign(null);

            result = crypt.verify(null, null);

            log.info("Es valido?: " + result);
            Assert.assertEquals(true, result);

        } catch (Exception ex) {
            log.error("Error General", ex);
            fail("Error no experado");
        }
    }
}
