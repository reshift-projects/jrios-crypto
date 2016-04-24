package com.novatronic.components.ImplFile;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.support.KeySupport;

public class FileCipher extends BasicCipher<Object, Boolean> {

    private static final Logger log = LoggerFactory.getLogger(FileCipher.class);

    private String[] ENC_PROPS = {"encFileIn", "encFileOut", "encSignFileOut", "encKeyFile", "encSignKeyFile", "encSignAlg",
        "encAlg", "encSignKeyAlias", "encKeystoreLoadPassword", "encPrivatePassword"};
    private String[] DEC_PROPS = {"decFileIn", "decFileOut", "decKeyFile", "decAlg"};
    private String[] VER_PROPS = {"verFileIn", "verSignFile", "verSignKeyFile", "verSignKeyAlias", "verKeystoreLoadPassword", "verSignAlg"};

    public FileCipher() {
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
            log.debug("Iniciando verificar firma de archivo");

            FileSign signer;
            Key publicKey;
            //verificar datos
            validarDatos(VER_PROPS);
            //Leer llave publica de archivo
            publicKey = getPublicKey(prop.getProperty("verSignKeyFile"),
                    prop.getProperty("verSignKeyAlias"),
                    prop.getProperty("verKeystoreLoadPassword"));

            signer = new FileSign(prop.getProperty("verSignAlg"));
            result = signer.verify(prop.getProperty("verFileIn"), (PublicKey) publicKey, prop.getProperty("verSignFile"));
        } catch (Exception e) {
            throw new CryptoException("Error al intentar verificar firma de archivo", e);
        }
        return result;
    }

    public void decrypt() {
        String algoritm;
        try {
            log.debug("Inciando descifrar archivo segun properties");
            Key symmetricKey;
            CryptFile crypt;

            //verificar datos
            validarDatos(DEC_PROPS);

            //leer llave simetrica de archivo
            algoritm = prop.getProperty("decAlg");
            symmetricKey = KeySupport.getSymmetricKey(prop.getProperty("decKeyFile"), algoritm);//getSymmetricKey(prop.getProperty("decKeyFile"));

            //descifrar file
            crypt = new CryptFile(algoritm, symmetricKey);
            crypt.decrypt(new File(prop.getProperty("decFileIn")), new File(prop.getProperty("decFileOut")));
        } catch (Exception ex) {
            throw new CryptoException("Error al intentar descifrar archivo", ex);
        }
    }

    public void sign() {

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

            //sign File
            signOut = signer.sign(prop.getProperty("encFileIn"), (PrivateKey) privateKey);
            fos = new FileOutputStream(prop.getProperty("encSignFileOut"));
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
            log.debug("Iniciando cifrado de archivo segun properties");
            byte[] signOut;
            FileOutputStream fos;
            //FileSign signer;
            CryptFile crypt;
            Key privateKey;
            Key symmetricKey;

            //verificar datos
            validarDatos(ENC_PROPS);

            //signer = new FileSign(prop.getProperty("encSignAlg"));
            //Leer llave privada de archivo		
            privateKey = getPrivateKey(prop.getProperty("encSignKeyFile"),
                    prop.getProperty("encSignKeyAlias"),
                    prop.getProperty("encKeystoreLoadPassword"),
                    prop.getProperty("encPrivatePassword"));

            //leer llave simetrica de archivo
            algoritm = prop.getProperty("encAlg");
            symmetricKey = KeySupport.getSymmetricKey(prop.getProperty("encKeyFile"), algoritm);//getSymmetricKey(prop.getProperty("encKeyFile"));

            //cifrar con filein, fileout, algoritmo, llave		
            crypt = new CryptFile(algoritm, symmetricKey);
            crypt.encrypt(new File(prop.getProperty("encFileIn")), new File(prop.getProperty("encFileOut")));

        } catch (Exception ex) {
            throw new CryptoException("Error al intentar cifrar archivo", ex);
        }

    }

}
