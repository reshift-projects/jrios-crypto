package com.novatronic.cripto.type;

import com.novatronic.cripto.resource.SignatureResource;
import com.novatronic.cripto.resource.CipherResource;
import com.novatronic.cripto.resource.KeyResource;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.cripto.exception.CryptoException;
import java.util.Properties;
import com.novatronic.cripto.controller.Cripto;

public class FileCripto implements Cripto<Object, Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCripto.class);

    private final String[] ENC_PROPS = {"encFileIn", "encFileOut", "encSignFileOut", "encKeyFile", "encSignKeyFile", "encSignAlg",
        "encAlg", "encSignKeyAlias", "encKeystoreLoadPassword", "encPrivatePassword"};
    private final String[] DEC_PROPS = {"decFileIn", "decFileOut", "decKeyFile", "decAlg"};
    private final String[] VER_PROPS = {"verFileIn", "verSignFile", "verSignKeyFile", "verSignKeyAlias", "verKeystoreLoadPassword", "verSignAlg"};

    private ConfigCripto config;
    
    @Override
    public void config(Properties props) throws CryptoException {
        config = new ConfigCripto(props);
    }
    
    @Override
    public boolean verify(Object in, Boolean out) throws CryptoException {
        return verify();
    }

    @Override
    public Boolean sign(Object in) throws CryptoException {
        sign();
        return Boolean.TRUE;
    }

    @Override
    public Boolean encrypt(Object in) throws CryptoException {
        encrypt();
        return Boolean.TRUE;
    }

    @Override
    public Boolean decrypt(Object in) throws CryptoException {
        decrypt();
        return Boolean.TRUE;
    }

    public boolean verify() {
        boolean result = false;
        try {
            LOGGER.debug("Iniciando verificar firma de archivo");

            SignatureResource signer;
            Key publicKey;
            //verificar datos
            config.validate(VER_PROPS);
            //Leer llave publica de archivo
            publicKey = KeyResource.getPublicKey(config.get("verSignKeyFile"),
                    config.get("verSignKeyAlias"),
                    config.get("verKeystoreLoadPassword"));

            signer = new SignatureResource(config.get("verSignAlg"));
            result = signer.verify(config.get("verFileIn"), (PublicKey) publicKey, config.get("verSignFile"));
        } catch (Exception e) {
            throw new CryptoException("Error al intentar verificar firma de archivo", e);
        }
        return result;
    }

    public void decrypt() {
        String algoritm;
        try {
            LOGGER.debug("Inciando descifrar archivo segun properties");
            Key symmetricKey;
            CipherResource crypt;

            //verificar datos
            config.validate(DEC_PROPS);

            //leer llave simetrica de archivo
            algoritm = config.get("decAlg");
            symmetricKey = KeyResource.getSymmetricKey(config.get("decKeyFile"), algoritm);//getSymmetricKey(config.get("decKeyFile"));

            //descifrar file
            crypt = new CipherResource(algoritm, symmetricKey);
            crypt.decrypt(new File(config.get("decFileIn")), new File(config.get("decFileOut")));
        } catch (Exception ex) {
            throw new CryptoException("Error al intentar descifrar archivo", ex);
        }
    }

    public void sign() {

        try {
            LOGGER.debug("Iniciando cifrado de archivo segun properties");
            byte[] signOut;
            FileOutputStream fos;
            SignatureResource signer;
            CipherResource crypt;
            Key privateKey;
            Key symmetricKey;

            //verificar datos
            config.validate(ENC_PROPS);

            signer = new SignatureResource(config.get("encSignAlg"));

            //Leer llave privada de archivo
            privateKey = KeyResource.getPrivateKey(config.get("encSignKeyFile"),
                    config.get("encSignKeyAlias"),
                    config.get("encKeystoreLoadPassword"),
                    config.get("encPrivatePassword"));

            //sign File
            signOut = signer.sign(config.get("encFileIn"), (PrivateKey) privateKey);
            fos = new FileOutputStream(config.get("encSignFileOut"));
            fos.write(signOut);
            fos.flush();
            fos.close();

        } catch (Exception ex) {
            throw new CryptoException("Error al intentar cifrar archivo", ex);
        }

    }

    public void encrypt() {
        String algoritm;
        try {
            LOGGER.debug("Iniciando cifrado de archivo segun properties");
            byte[] signOut;
            FileOutputStream fos;
            //FileSign signer;
            CipherResource crypt;
            Key privateKey;
            Key symmetricKey;

            //verificar datos
            config.validate(ENC_PROPS);

            //signer = new SignatureResource(config.get("encSignAlg"));
            //Leer llave privada de archivo		
            privateKey = KeyResource.getPrivateKey(config.get("encSignKeyFile"),
                    config.get("encSignKeyAlias"),
                    config.get("encKeystoreLoadPassword"),
                    config.get("encPrivatePassword"));

            //leer llave simetrica de archivo
            algoritm = config.get("encAlg");
            symmetricKey = KeyResource.getSymmetricKey(config.get("encKeyFile"), algoritm);//getSymmetricKey(config.get("encKeyFile"));

            //cifrar con filein, fileout, algoritmo, llave		
            crypt = new CipherResource(algoritm, symmetricKey);
            crypt.encrypt(new File(config.get("encFileIn")), new File(config.get("encFileOut")));

        } catch (Exception ex) {
            throw new CryptoException("Error al intentar cifrar archivo", ex);
        }

    }

}
