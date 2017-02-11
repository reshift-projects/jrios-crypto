/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.resource;

import com.novatronic.cripto.exception.CryptoException;
import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ricardo
 */
public final class KeyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyResource.class);

    private KeyResource() {
    }

    public static Key getPrivateKey(String privateKeyFile, String alias, String keystoreLoadPassword, String privatePassword) {
        Key privateKey;
        try {
            LOGGER.debug("Obteniendo clave privada de keystore");
            KeyStore ks;
            FileInputStream fisKey;

            // Consiguiendo la llave Privada antes generada
            ks = KeyStore.getInstance(KeyStore.getDefaultType());
            fisKey = new FileInputStream(privateKeyFile);
            ks.load(fisKey, keystoreLoadPassword.toCharArray());
            fisKey.close();

            // llave Privada
            privateKey = (PrivateKey) ks.getKey(alias, privatePassword.toCharArray());
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.OBTENER_LLAVE_PRIVADA, "Error al obtener llave privada", ex);
        }
        return privateKey;
    }

    public static Key getPublicKey(String signKeyFile, String signKeyAlias, String keystoreLoadPassword) {
        Key publicKey;
        try {
            LOGGER.debug("Obteniendo clave publica de keystore");
            KeyStore ks;
            FileInputStream fisKey;
            Certificate cert;

            ks = KeyStore.getInstance(KeyStore.getDefaultType());
            fisKey = new FileInputStream(signKeyFile);
            ks.load(fisKey, keystoreLoadPassword.toCharArray());
            fisKey.close();

            // llave Publica
            cert = ks.getCertificate(signKeyAlias);
            publicKey = cert.getPublicKey();
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.OBTENER_LLAVE_PUBLICA, "Error al obtener llave publica", ex);
        }

        return publicKey;
    }

    public static Key getSymmetricKey(String symmetricKeyFile, String algoritm) {
        byte[] encoded;
        try {
            //log.debug("Obteniendo clave simetrica de archivo");
            FileInputStream fis;

            File file;

            file = new File(symmetricKeyFile);
            encoded = new byte[(int) file.length()];
            fis = new FileInputStream(file);

            fis.read(encoded);
            fis.close();
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.OBTENER_LLAVE_SIMETRICA, "Error al obtener llave simetrica", ex);
        }

        return createSymmetricKey(encoded, algoritm);

    }

    private static Key createSymmetricKey(byte[] symmetricKeyBytes, String algoritm) {
        SecretKeySpec key = new SecretKeySpec(symmetricKeyBytes, algoritm);
        LOGGER.trace("Obteniendo llave [{}]", new String(key.getEncoded()));
        return key;
    }
}
