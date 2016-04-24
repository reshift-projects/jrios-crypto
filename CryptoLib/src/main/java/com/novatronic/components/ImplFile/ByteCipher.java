package com.novatronic.components.ImplFile;

import java.io.FileOutputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.support.CryptFileSupport;
import com.novatronic.components.support.KeySupport;

public class ByteCipher extends BasicCipher<byte[], byte[]> {

    private static final Logger log = LoggerFactory.getLogger(ByteCipher.class);

    private static final String[] ENC_PROPS = {"encKeyFile", "encSignKeyFile", "encSignAlg",
        "encAlg", "encSignKeyAlias", "encKeystoreLoadPassword", "encPrivatePassword"};
    private static final String[] DEC_PROPS = {"decKeyFile", "decAlg"};
    private static final String[] VER_PROPS = {"verSignFile", "verSignKeyFile", "verSignKeyAlias", "verKeystoreLoadPassword", "verSignAlg"};

    public ByteCipher() {
    }

    
    
    public byte[] sign(byte[] in) throws CryptoException {
        
        try {
            log.debug("Iniciando cifrado de archivo segun properties");
            byte[] signOut;
            FileOutputStream fos;
            FileSign signer;
            CryptFile crypt;
            Key privateKey;
            Key symmetricKey;

            //verificar datos
            validarDatos(ENC_PROPS);
            signer = new FileSign(prop.getProperty("encSignAlg"));

            //Leer llave privada de archivo
            privateKey = getPrivateKey(prop.getProperty("encSignKeyFile"),
                    prop.getProperty("encSignKeyAlias"),
                    prop.getProperty("encKeystoreLoadPassword"),
                    prop.getProperty("encPrivatePassword"));

            //sign 
            signOut = signer.sign(in, (PrivateKey) privateKey);
            return signOut;

        } catch (Exception ex) {
            throw new CryptoException("Error al intentar cifrar arreglo bytes", ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] in) throws CryptoException {
        Key symmetricKey;
        String encKeyFile;
        String algoritm;
        byte[] out;

        try {
            log.debug("Iniciando cifrado de bytes segun arreglo de bytes");
            CryptFile crypt;
            //leer llave simetrica de archivo
            algoritm = prop.getProperty("encAlg");

            symmetricKey = KeySupport.getSymmetricKey(prop.getProperty("encKeyFile"), algoritm);

            //cifrar con filein, fileout, algoritmo, llave		
            out = CryptFileSupport.encryptBytes(algoritm, symmetricKey, in);
            return out;
        } catch (Exception ex) {
            throw new CryptoException("Error al intentar cifrar flujo bytes", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] in) throws CryptoException {
        String algoritm;
        byte[] out;
        try {
            log.debug("Inciando descifrar archivo segun properties");
            Key symmetricKey;
            CryptFile crypt;

            //verificar datos
            validarDatos(DEC_PROPS);
            //leer llave simetrica de archivo
            algoritm = prop.getProperty("decAlg");

            symmetricKey = KeySupport.getSymmetricKey(prop.getProperty("decKeyFile"), algoritm);

            //descifrar file
            out = CryptFileSupport.decryptBytes(algoritm, symmetricKey, in);
            return out;
        } catch (Exception ex) {
            throw new CryptoException("Error al intentar descifrar arreglo bytes", ex);
        }
    }

    @Override
    public boolean verify(byte[] in) throws CryptoException {
        boolean result = false;
        try {
            log.debug("Iniciando verificar firma de arreglo bytes");

            FileSign signer;
            Key publicKey;
            //verificar datos
            validarDatos(VER_PROPS);
            //Leer llave publica de archivo
            publicKey = getPublicKey(prop.getProperty("verSignKeyFile"),
                    prop.getProperty("verSignKeyAlias"),
                    prop.getProperty("verKeystoreLoadPassword"));

            signer = new FileSign(prop.getProperty("verSignAlg"));
            result = signer.verify(in, (PublicKey) publicKey, prop.getProperty("verSignFile"));
        } catch (Exception e) {
            throw new CryptoException("Error al intentar verificar firma de archivo", e);
        }
        return result;
    }

}
