package com.novatronic.cripto.service.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketManager
        implements Runnable
{
    private BlockingQueue<Socket> colaSocket;
    private int port;
    private ServerSocket serverSocket;

    public SocketManager(int port, BlockingQueue<Socket> colaSocket)
    {
        this.port = port;
        this.colaSocket = colaSocket;
    }

    public void init()
    {
        try
        {
            this.serverSocket = new ServerSocket(this.port);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void accept()
    {
        try
        {
            this.colaSocket.offer(this.serverSocket.accept());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void down()
    {
        try
        {
            this.serverSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        init();
        while (Boolean.TRUE.booleanValue()) {
            accept();
        }
        down();
    }
}
