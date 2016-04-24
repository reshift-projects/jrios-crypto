/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.support;

import com.novatronic.components.exceptions.CryptoException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author rcastillejo
 */
public class KeySupport {

    public static byte[] serializeByte(Object obj) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        out.close();
        byteOut.close();
        return byteOut.toByteArray();
    }

    public static <C> C deserializeBytes(byte[] bytes, Class<C> claz) throws IOException, ClassNotFoundException {
        C obj = null;
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        obj = claz.cast(in.readObject());
        in.close();
        return obj;
    }

    public static Key getSymmetricKey(String symmetricKeyFile, String algoritm) {
        byte[] encoded;
        String encode = "";
        try {
            //log.debug("Obteniendo clave simetrica de archivo");
            FileInputStream fis;

            File file;

            file = new File(symmetricKeyFile);
            encoded = new byte[(int) file.length()];
            fis = new FileInputStream(file);

            fis.read(encoded);
            fis.close();

            return createSymmetricKey(encoded, algoritm);
        } catch (Exception ex) {
            throw new CryptoException("Error al obtener clave", ex);
        }

    }

    private static Key createSymmetricKey(byte[] symmetricKeyBytes, String algoritm) {
        return new SecretKeySpec(symmetricKeyBytes, algoritm);
    }

}
