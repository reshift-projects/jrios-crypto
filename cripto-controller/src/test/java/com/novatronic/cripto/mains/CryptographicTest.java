/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.mains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

public class CryptographicTest {

    private static final String CONFIG_FILE = "CipherOptions_Bytes.properties";

    private static final String ALGO = "DES";
    public static void main(String[] args) throws Exception {
/*
        ParameterLoader.init(ResourceHelper.find(CONFIG_FILE));
        String algorithm = "DES";
        String llaveSimetrica = "prueba123456789";

        SecretKeySpec key = new SecretKeySpec(llaveSimetrica.getBytes(), algorithm);

        FileSupport.writeFile(new File("D:/POCCrypt", algorithm + "Key"), key.getEncoded());
        */
        
            SecretKey originalKey = generateKey();
            File file = new File("D:/POCCrypt", ALGO.toLowerCase() + "Key");
            saveKey(originalKey, file);
            /*SecretKey persistedKey = loadKey(new File("D:/POCCrypt", "aesKey"));
            System.out.println("persistedKey" + new String(persistedKey.getEncoded()));*/
    }
    
    //private static final int KEYSZ = 256;// 128 default; 192 and 256 also possible
    private static final int KEYSZ = 56;// 128 default; 192 and 256 also possible
    
    public static SecretKey generateKey() throws NoSuchAlgorithmException
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGO);
        keyGenerator.init(KEYSZ); 
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    
    public static void saveKey(SecretKey key, File file) throws IOException
    {
        byte[] encoded = key.getEncoded();
        //char[] hex = encodeHex(encoded);
        //String data = String.valueOf(encoded);
        //writeStringToFile(file, data);
        FileOutputStream out = new FileOutputStream(file);
        out.write(encoded);
        out.flush();
        out.close();
    }
    
    public static SecretKey loadKey(File file) throws IOException
    {
        String data = new String(readFileToByteArray(file));
        char[] hex = data.toCharArray();
        byte[] encoded;
        /*try
        {
            encoded = decodeHex(hex);
        }
        catch (DecoderException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }*/
        SecretKey key = new SecretKeySpec(data.getBytes(), ALGO);
        return key;
    }
}
