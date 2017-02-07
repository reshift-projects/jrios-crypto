package com.novatronic.components.ImplFile;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.support.CryptFileSupport;
import com.novatronic.components.support.KeySupport;

public class ByteCipher extends BasicCipher<byte[], byte[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteCipher.class);

    private static final String[] ENC_PROPS = {"encKeyFile", "encAlg"};
    private static final String[] DEC_PROPS = {"decKeyFile", "decAlg"};
    private static final String[] SIG_PROPS = {"encSignKeyFile", "encSignAlg", "encSignKeyAlias", "encKeystoreLoadPassword", "encPrivatePassword"};
    private static final String[] VER_PROPS = {"verSignKeyFile", "verSignAlg", "verSignKeyAlias", "verKeystoreLoadPassword"};

    public ByteCipher() {
    }

    @Override
    public byte[] sign(byte[] in) throws CryptoException {

        LOGGER.debug("Iniciando cifrado de archivo segun properties");
        byte[] signOut;
        FileSign signer;
        Key privateKey;

        //verificar datos
        validarDatos(SIG_PROPS);
        signer = new FileSign(prop.getProperty("encSignAlg"));

        //Leer llave privada de archivo
        privateKey = getPrivateKey(prop.getProperty("encSignKeyFile"),
                prop.getProperty("encSignKeyAlias"),
                prop.getProperty("encKeystoreLoadPassword"),
                prop.getProperty("encPrivatePassword"));

        try {
            //sign 
            signOut = signer.sign(in, (PrivateKey) privateKey);
            return signOut;
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.FIRMAR, "Error al firmar el mensaje", ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] in) throws CryptoException {
        Key symmetricKey;
        String algoritm;
        byte[] out;

        LOGGER.debug("Iniciando cifrado de bytes segun arreglo de bytes");

        //verificar datos
        validarDatos(ENC_PROPS);

        //leer llave simetrica de archivo
        algoritm = prop.getProperty("encAlg");
        symmetricKey = KeySupport.getSymmetricKey(prop.getProperty("encKeyFile"), algoritm);

        try {
            //cifrar con filein, fileout, algoritmo, llave		
            out = CryptFileSupport.encryptBytes(algoritm, symmetricKey, in);
            return out;
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.CIFRAR, "Error al cifrar el mensaje", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] in) throws CryptoException {
        String algoritm;
        byte[] out;
        LOGGER.debug("Inciando descifrar archivo segun properties");
        Key symmetricKey;

        //verificar datos
        validarDatos(DEC_PROPS);
        //leer llave simetrica de archivo
        algoritm = prop.getProperty("decAlg");

        symmetricKey = KeySupport.getSymmetricKey(prop.getProperty("decKeyFile"), algoritm);

        try {
            //descifrar file
            out = CryptFileSupport.decryptBytes(algoritm, symmetricKey, in);
            return out;
        } catch (Exception ex) {
            throw new CryptoException(CryptoException.DESCRIFRAR, "Error al descifrar el mensaje", ex);
        }
    }

    @Override
    public boolean verify(byte[] in, byte[] out) throws CryptoException {
        boolean result = false;
        FileSign signer;
        Key publicKey;

        LOGGER.debug("Iniciando verificar firma de arreglo bytes");

        //verificar datos
        validarDatos(VER_PROPS);
        //Leer llave publica de archivo
        publicKey = getPublicKey(prop.getProperty("verSignKeyFile"),
                prop.getProperty("verSignKeyAlias"),
                prop.getProperty("verKeystoreLoadPassword"));

        try {
            signer = new FileSign(prop.getProperty("verSignAlg"));
            result = signer.verify(in, (PublicKey) publicKey, out);
            return result;
        } catch (Exception e) {
            throw new CryptoException(CryptoException.VERIFICAR, "Error al verificar la firma del mensaje", e);
        }
    }

}
