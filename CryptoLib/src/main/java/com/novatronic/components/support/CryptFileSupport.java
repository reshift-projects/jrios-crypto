package com.novatronic.components.support;

import com.novatronic.components.ImplFile.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptFileSupport {

    private static final Logger log = LoggerFactory.getLogger(CryptFileSupport.class);

    public static byte[] encryptBytes(String algoritm, Key key, byte[] in) throws GeneralSecurityException, IOException {
        CryptFile crypt;
        //cifrar con bytesin, fileout, algoritmo, llave		
        crypt = new CryptFile(algoritm, key);
        return crypt.encrypt(in);
    }

    public static byte[] decryptBytes(String algoritm, Key key, byte[] in) throws GeneralSecurityException, IOException {
        CryptFile crypt;
        //cifrar con bytesin, fileout, algoritmo, llave		
        crypt = new CryptFile(algoritm, key);
        return crypt.decrypt(in);
    }

}
