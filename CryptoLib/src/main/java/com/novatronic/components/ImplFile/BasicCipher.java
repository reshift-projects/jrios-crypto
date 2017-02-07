package com.novatronic.components.ImplFile;

import com.novatronic.components.crypto.Crypto;
import java.security.Key;
import java.security.PrivateKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.exceptions.ParametersCryptoException;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Properties;

public abstract class BasicCipher<S, T> implements Crypto<S, T> {

    private static final Logger log = LoggerFactory.getLogger(BasicCipher.class);

    protected Properties prop;

    public BasicCipher() {
    }

    @Override
    public void config(Properties props) throws CryptoException {
        this.prop = props;
    }

    protected void validarDatos(String[] enc_props) {
        for (String value : enc_props) {
            if (!(prop.containsKey(value) && !prop.get(value).equals(""))) {
                throw new ParametersCryptoException("No se tienen suficientes valores para la operacion");
            }
        }
    }

    protected Key getPrivateKey(String privateKeyFile, String alias, String keystoreLoadPassword, String privatePassword) {
        Key privateKey;
        try {
            log.debug("Obteniendo clave privada de keystore");
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

    protected Key getPublicKey(String signKeyFile, String signKeyAlias, String keystoreLoadPassword) {
        Key publicKey;
        try {
            log.debug("Obteniendo clave publica de keystore");
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
}
