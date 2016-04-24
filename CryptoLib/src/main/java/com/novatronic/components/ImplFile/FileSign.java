package com.novatronic.components.ImplFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.log4j.Logger;

public class FileSign {

    private static final Logger log = Logger.getLogger(FileSign.class);

    private String algorithm = "SHA1withRSA";

    public FileSign(String algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] sign(byte[] data, PrivateKey prvKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        log.debug("Creando firma a partir de arreglo de bytes");
        ByteArrayInputStream fis = new ByteArrayInputStream(data);
        return sign(fis, prvKey);
    }

    public byte[] sign(String datafile, PrivateKey prvKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        log.debug("Creando firma a partir de archivo");
        FileInputStream fis = new FileInputStream(datafile);
        return sign(fis, prvKey);
    }

    public byte[] sign(InputStream fis, PrivateKey prvKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(prvKey);
        byte[] dataBytes = new byte[1024];
        int nread = fis.read(dataBytes);
        while (nread > 0) {
            sig.update(dataBytes, 0, nread);
            nread = fis.read(dataBytes);
        }
        fis.close();
        return sig.sign();
    }

    public boolean verify(byte[] data, PublicKey pubKey, byte[] sigbytes) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        ByteArrayInputStream fis = new ByteArrayInputStream(data);
        return verify(fis, pubKey, sigbytes);
    }
    
    public boolean verify(String datafile, PublicKey pubKey, String sigbytes) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        FileInputStream fis = new FileInputStream(datafile);
        FileInputStream fisOut = new FileInputStream(sigbytes);
        File sigFile = new File(sigbytes);
        FileInputStream fisign = new FileInputStream(sigFile);
        byte[] sigBytes = new byte[(int) sigFile.length()];
        fisign.read(sigBytes);
        fisign.close();
        return verify(fis, pubKey, sigBytes);
    }
    
    public boolean verify(InputStream fis, PublicKey pubKey, byte[]  sigBytes) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        log.debug("Verificando firma");
        Signature sig = Signature.getInstance(algorithm);
        sig.initVerify(pubKey);
        byte[] dataBytes = new byte[1024];
        int nread = fis.read(dataBytes);
        while (nread > 0) {
            sig.update(dataBytes, 0, nread);
            nread = fis.read(dataBytes);
        };
        fis.close();

        return sig.verify(sigBytes);
    }
    
    

}
