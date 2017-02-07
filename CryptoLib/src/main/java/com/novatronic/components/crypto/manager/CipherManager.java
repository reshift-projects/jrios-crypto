package com.novatronic.components.crypto.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CipherManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CipherManager.class);

    private final Cipher cipher;
    private final SecretKeySpec keySpec;

    public CipherManager(String algorithm, Key secKey) throws GeneralSecurityException {
        // instancia cipher
        this.keySpec = (SecretKeySpec) secKey;

        this.cipher = Cipher.getInstance(algorithm);
    }

    public byte[] encrypt(byte[] in) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        LOGGER.debug("Cifrando arreglo bytes {}", in.length);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(in);
    }

    public void encrypt(File in, File out) throws IOException, InvalidKeyException {
        LOGGER.debug("Cifrando archivo {}", in.getName());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        FileInputStream is = new FileInputStream(in);
        CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), cipher);

        copyAndClose(is, os);
    }

    public byte[] decrypt(byte[] in) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        LOGGER.debug("Descifrando arreglo bytes {}", in.length);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        return cipher.doFinal(in);
    }

    public void decrypt(File in, File out) throws IOException, InvalidKeyException {
        LOGGER.debug("Descifrando archivo {}", in.getName());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        CipherInputStream is = new CipherInputStream(new FileInputStream(in), cipher);
        FileOutputStream os = new FileOutputStream(out);
        copyAndClose(is, os);
    }

    private void copyAndClose(InputStream is, OutputStream os) throws IOException {
        copy(is, os);
        is.close();
        os.close();

    }

    private void copy(InputStream is, OutputStream os) throws IOException {
        int i;
        byte[] b = new byte[1024];
        while ((i = is.read(b)) != -1) {
            os.write(b, 0, i);
        }
    }
}
