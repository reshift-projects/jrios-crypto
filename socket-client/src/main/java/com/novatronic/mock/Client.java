package com.novatronic.mock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import org.apache.commons.io.FileUtils;

public class Client
{
    public static void main(String[] args)
            throws Exception
    {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        try
        {
            Socket socket = new Socket(host, port);

            byte[] requestBytes = getRequestBytes(args[2], args[3]);

            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

            os.writeShort(requestBytes.length);
            os.write(requestBytes);

            DataInputStream in = new DataInputStream(socket.getInputStream());

            short lengthResponse = in.readShort();

            byte[] bytes = new byte[lengthResponse];

            in.readFully(bytes);

            os.close();
            in.close();
            socket.close();

            System.out.println("respuesta [" + new String(bytes, "UTF-8") + "]");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] getRequestBytes(String tipo, String datasource)
            throws IOException
    {
        if (tipo.equalsIgnoreCase("text")) {
            return datasource.getBytes("UTF-8");
        }
        if (tipo.equalsIgnoreCase("file"))
        {
            File file = new File(datasource);
            return FileUtils.readFileToByteArray(file);
        }
        throw new RuntimeException("No se especifico el dato a procesar");
    }
}
