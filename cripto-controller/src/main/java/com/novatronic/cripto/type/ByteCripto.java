package com.novatronic.cripto.type;

import com.novatronic.cripto.resource.SignatureResource;
import com.novatronic.cripto.resource.CipherResource;
import com.novatronic.cripto.resource.KeyResource;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.cripto.exception.CryptoException;
import java.util.Properties;
import com.novatronic.cripto.controller.Cripto;

public class ByteCripto implements Cripto<byte[], byte[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteCripto.class);

    private static final String[] ENC_PROPS = {"encKeyFile", "encAlg"};
    private static final String[] DEC_PROPS = {"decKeyFile", "decAlg"};
    private static final String[] SIG_PROPS = {"encSignKeyFile", "encSignAlg", "encSignKeyAlias", "encKeystoreLoadPassword", "encPrivatePassword"};
    private static final String[] VER_PROPS = {"verSignKeyFile", "verSignAlg", "verSignKeyAlias", "verKeystoreLoadPassword"};

    private ConfigCripto config;

    @Override
    public void config(Properties props) throws CryptoException {
        config = new ConfigCripto(props);
    }

    @Override
    public byte[] sign(byte[] in) throws CryptoException {

        LOGGER.debug("Iniciando cifrado de archivo segun properties");
        byte[] signOut;
        SignatureResource signer;
        Key privateKey;

        //verificar datos
        config.validate(SIG_PROPS);

        //Leer llave privada de archivo
        privateKey = KeyResource.getPrivateKey(config.get("encSignKeyFile"),
                config.get("encSignKeyAlias"),
                config.get("encKeystoreLoadPassword"),
                config.get("encPrivatePassword"));

        try {
            //sign 
            signer = new SignatureResource(config.get("encSignAlg"));
            signOut = signer.sign(in, (PrivateKey) privateKey);
            return signOut;
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.FIRMAR, "Error al firmar el mensaje", ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] in) throws CryptoException {
        CipherResource encrypter;
        Key symmetricKey;
        String algoritm;
        byte[] out;

        LOGGER.debug("Iniciando cifrado de bytes segun arreglo de bytes");

        //verificar datos
        config.validate(ENC_PROPS);

        //leer llave simetrica de archivo
        algoritm = config.get("encAlg");
        symmetricKey = KeyResource.getSymmetricKey(config.get("encKeyFile"), algoritm);

        try {
            //cifrar con filein, fileout, algoritmo, llave		
            encrypter = new CipherResource(algoritm, symmetricKey);
            out = encrypter.encrypt(in);
            return out;
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.CIFRAR, "Error al cifrar el mensaje", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] in) throws CryptoException {
        CipherResource encrypter;
        String algoritm;
        byte[] out;
        LOGGER.debug("Inciando descifrar archivo segun properties");
        Key symmetricKey;

        //verificar datos
        config.validate(DEC_PROPS);
        //leer llave simetrica de archivo
        algoritm = config.get("decAlg");

        symmetricKey = KeyResource.getSymmetricKey(config.get("decKeyFile"), algoritm);

        try {
            //descifrar file
            encrypter = new CipherResource(algoritm, symmetricKey);
            out = encrypter.decrypt(in);
            return out;
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.DESCRIFRAR, "Error al descifrar el mensaje", ex);
        }
    }

    @Override
    public boolean verify(byte[] in, byte[] out) throws CryptoException {
        boolean result = false;
        SignatureResource signer;
        Key publicKey;

        LOGGER.debug("Iniciando verificar firma de arreglo bytes");

        //verificar datos
        config.validate(VER_PROPS);
        //Leer llave publica de archivo
        publicKey = KeyResource.getPublicKey(config.get("verSignKeyFile"),
                config.get("verSignKeyAlias"),
                config.get("verKeystoreLoadPassword"));

        try {
            signer = new SignatureResource(config.get("verSignAlg"));
            result = signer.verify(in, (PublicKey) publicKey, out);
            return result;
        } catch (Exception e) {
            throw new CryptoException(CryptoException.VERIFICAR, "Error al verificar la firma del mensaje", e);
        }
    }

}
