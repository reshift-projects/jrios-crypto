package com.novatronic.components.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.novatronic.components.exceptions.CryptoException;
import java.net.URISyntaxException;

public class FileSupport {

    public static byte[] readFile(String fileName) {
        File fileIn;
        byte[] in;

        try {
            fileIn = new File(ResourceHelper.find(fileName).toURI());
            if (fileIn.exists()) {
                in = new byte[(int) fileIn.length()];
                FileInputStream fis = new FileInputStream(fileIn);
                fis.read(in);
                fis.close();

                return in;
            } else {
                throw new CryptoException("Archivo no existe");
            }
        } catch (URISyntaxException ex) {
            throw new CryptoException("Error en el cifrado de archivo", ex);
        } catch (IOException ex) {
            throw new CryptoException("Error en el cifrado de archivo", ex);
        } catch (CryptoException ex) {
            throw new CryptoException("Error en el cifrado de archivo", ex);
        }
    }

    public static void writeFile(File file, byte[] out) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(out);
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            throw new CryptoException("Error al escribir en el archivo:" + file, ex);
        }
    }

    public static void writeFile(String fileName, byte[] out) {
        writeFile(new File(fileName), out);
    }
}
