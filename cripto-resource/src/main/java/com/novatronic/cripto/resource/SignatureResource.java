package com.novatronic.cripto.resource;

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
import javax.crypto.KeyGenerator;

import org.apache.log4j.Logger;

public class SignatureResource {

    private static final Logger log = Logger.getLogger(SignatureResource.class);

    private final Signature signature;

    public SignatureResource(String algorithm) throws NoSuchAlgorithmException {
        this.signature = Signature.getInstance(algorithm);
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
        cargarAlgoritmoFirma(prvKey);
        byte[] dataBytes = new byte[1024];
        int nread = fis.read(dataBytes);
        while (nread > 0) {
            signature.update(dataBytes, 0, nread);
            nread = fis.read(dataBytes);
        }
        fis.close();
        return signature.sign();
    }

    public boolean verify(byte[] data, PublicKey pubKey, byte[] sigbytes) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        ByteArrayInputStream fis = new ByteArrayInputStream(data);
        return verify(fis, pubKey, sigbytes);
    }

    public boolean verify(String datafile, PublicKey pubKey, String sigbytes) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
        keyGenerator.init(96);
        Key blowfishKey = keyGenerator.generateKey();
        
        FileInputStream fis = new FileInputStream(datafile);
        FileInputStream fisOut = new FileInputStream(sigbytes);
        File sigFile = new File(sigbytes);
        FileInputStream fisign = new FileInputStream(sigFile);
        byte[] sigBytes = new byte[(int) sigFile.length()];
        fisign.read(sigBytes);
        fisign.close();
        return verify(fis, pubKey, sigBytes);
    }

    public boolean verify(InputStream fis, PublicKey pubKey, byte[] sigBytes) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        log.debug("Verificando firma");
        cargarAlgoritmoFirma(pubKey);

        byte[] dataBytes = new byte[1024];
        int nread = fis.read(dataBytes);
        while (nread > 0) {
            signature.update(dataBytes, 0, nread);
            nread = fis.read(dataBytes);
        }
        fis.close();

        return signature.verify(sigBytes);
    }

    private void cargarAlgoritmoFirma(PublicKey pubKey) throws NoSuchAlgorithmException, InvalidKeyException {
        signature.initVerify(pubKey);
    }

    private void cargarAlgoritmoFirma(PrivateKey privKey) throws NoSuchAlgorithmException, InvalidKeyException {
        signature.initSign(privKey);
    }

}
