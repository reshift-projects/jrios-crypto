package com.novatronic.components.ImplFile;

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

public class CryptFile {

    private static final Logger log = LoggerFactory.getLogger(CryptFile.class);

    private String algorithm = "AES";
    private Cipher cipher;
    private SecretKeySpec keySpec;

    public CryptFile(String algorithm, Key secKey) throws GeneralSecurityException {
        // instancia cipher
        this.algorithm = algorithm;
        this.keySpec = (SecretKeySpec) secKey;

        cipher = Cipher.getInstance(this.algorithm);
    }

    public byte[] encrypt(byte[] in) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        log.debug("Cifrando arreglo bytes {}", in.length);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(in);
    }

    public void encrypt(File in, File out) throws IOException, InvalidKeyException {
        log.debug("Cifrando archivo {}", in.getName());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        FileInputStream is = new FileInputStream(in);
        CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), cipher);

        copyAndClose(is, os);
    }

    public byte[] decrypt(byte[] in) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        log.debug("Descifrando arreglo bytes {}", in.length);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        return cipher.doFinal(in);
    }

    public void decrypt(File in, File out) throws IOException, InvalidKeyException {
        log.debug("Descifrando archivo {}", in.getName());
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
