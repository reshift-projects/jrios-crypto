/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.crypto.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GenerateKeyTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        //generando una clave aleatoria para el algoritmo AES
        KeyGenerator kgen = KeyGenerator.getInstance("DES");
        //kgen.init(256);
        kgen.init(56);
        SecretKey key = kgen.generateKey();
        byte[] aesKey = key.getEncoded();
        FileOutputStream out = new FileOutputStream("D:/POCCrypt/desKey");
        out.write(aesKey);
        out.flush();
        out.close();
    }

}
