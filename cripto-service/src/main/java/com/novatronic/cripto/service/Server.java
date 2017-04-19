package com.novatronic.cripto.service;

import com.novatronic.cripto.service.config.Configuration;
import com.novatronic.cripto.service.socket.SocketManager;
import com.novatronic.cripto.service.thread.ThreadPoolBuilder;
import com.novatronic.cripto.service.worker.CriptoService;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Server
{
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private static boolean yaEntroAnalizar = false;

    public static void shutdownControlador(String id, int numThreads, int timeForThreadsMili, int brokerIntervalMili)
    {
        boolean analizar = false;
        synchronized (Server.class)
        {
            if (!yaEntroAnalizar)
            {
                yaEntroAnalizar = true;
                analizar = true;
            }
        }
        boolean flagChequeo;
        if (analizar)
        {
            int retry = timeForThreadsMili / brokerIntervalMili;
            int c = 1;
            flagChequeo = true;
        }
    }

    public static void main(String[] args)
            throws Exception
    {
        Configuration configuration = new Configuration("broker.properties");

        log.info("Iniciando Servicio Evaluardor [" + configuration.getServerName() + "]");
        log.debug("Parametros: " + configuration);

        log.info("Iniciando Configuracion del Pool de Evaluadores");
        ThreadPoolBuilder.init();

        BlockingQueue<Socket> colaSocket = new LinkedBlockingQueue();

        executeWorkers(configuration, colaSocket);

        startManager(configuration, colaSocket);
    }

    private static void startManager(Configuration config, BlockingQueue<Socket> colaSocket)
    {
        SocketManager socketManager = new SocketManager(config.getPort(), colaSocket);
        Thread socketManagerThread = new Thread(socketManager);
        socketManagerThread.start();
    }

    private static void executeWorkers(Configuration config, BlockingQueue<Socket> colaSocket)
            throws Exception
    {
        int numThreads = config.getNumThreads();

        ThreadPoolExecutor threadPool = ThreadPoolBuilder.createThreadFactory(config);

        log.debug("Fabrica para procesos asicronos creado:" + threadPool.getCorePoolSize());
        for (int workerId = 0; workerId < numThreads; workerId++) {
            try
            {
                CriptoService service = new CriptoService(config.getConfig(), colaSocket);

                threadPool.execute(service);
                log.debug("Proceso Asincrono Worker[" + service + "] iniciado");
            }
            catch (Exception e)
            {
                log.error("No se pudo crear proceso Asincrono Worker[" + workerId + "]", e);
                throw e;
            }
        }
    }

    private static class SignalUSR2Handler
            implements SignalHandler
    {
        public void handle(Signal sig)
        {
            Server.log.info("Se ha recibido la senial USR2");

            Server.log.info("Se finaliza la maquina virtual...");
            System.exit(0);
        }
    }
}
