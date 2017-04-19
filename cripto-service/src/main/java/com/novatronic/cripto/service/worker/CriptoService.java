package com.novatronic.cripto.service.worker;

import com.novatronic.cripto.api.CriptoApi;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CriptoService
        implements Runnable
{
    public static final Logger LOGGER = LoggerFactory.getLogger(CriptoService.class);
    protected BlockingQueue<Socket> colaSocket;
    private CriptoApi api;

    public CriptoService(Properties configuration, BlockingQueue<Socket> colaSocket)
    {
        this.api = new CriptoApi(configuration);
        this.colaSocket = colaSocket;
    }

    public void run()
    {
        while (Boolean.TRUE) {
            try
            {
                Socket socket = obtenerDatosCola();
                LOGGER.debug("Socket obtenido ...");

                DataInputStream in = new DataInputStream(socket.getInputStream());

                short length = in.readShort();

                byte[] bytes = new byte[length];

                in.readFully(bytes);

                LOGGER.debug("bytes[" + new String(bytes, "UTF-8") + "]");

                byte[] responseBytes = this.api.process(bytes);

                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                os.writeShort(responseBytes.length);
                os.write(responseBytes);

                os.close();
                in.close();
                socket.close();
            }
            catch (Exception e)
            {
                LOGGER.error("Error", e);
            }
        }
    }

    public Socket obtenerDatosCola()
    {
        LOGGER.trace("Esperando leer de la cola ...");
        Socket socket = null;
        try
        {
            while(Boolean.TRUE)
            {
                LOGGER.trace("Verificando mensaje de la cola");
                socket = this.colaSocket.poll(1L, TimeUnit.SECONDS);
                if (socket != null) {
                    break;
                }
            }
        }
        catch (InterruptedException e)
        {
            LOGGER.warn("No se pudo leer de la cola");
        }
        LOGGER.debug("Mensjae obtenido de la cola [" + socket + "]");

        return socket;
    }
}
