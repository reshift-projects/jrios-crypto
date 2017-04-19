package com.novatronic.cripto.service.thread;

import com.novatronic.cripto.service.config.Configuration;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolBuilder
{
    private static ConcurrentHashMap<String, ThreadPoolExecutor> pools;
    private static AtomicInteger poolNumber;

    public static void init()
    {
        pools = new ConcurrentHashMap();
        poolNumber = new AtomicInteger(1);
    }

    public static ThreadPoolExecutor createThreadFactory(Configuration config)
    {
        String poolName = config.getServerName();
        if (!pools.containsKey(poolName))
        {
            int numberThreads = config.getNumThreads();

            ThreadFactory factory = new BaseThreadFactory(poolName, poolNumber.getAndIncrement());

            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numberThreads, numberThreads, 30L, TimeUnit.MINUTES, new SynchronousQueue(), factory);

            pools.put(poolName, threadPoolExecutor);
        }
        return (ThreadPoolExecutor)pools.get(poolName);
    }

    public static void execute(Configuration config, Runnable task)
    {
        ThreadPoolExecutor executor = createThreadFactory(config);
        executor.execute(task);
    }

    public static void destroy()
    {
        if ((pools != null) && (!pools.isEmpty()))
        {
            Enumeration<ThreadPoolExecutor> enumeration = pools.elements();
            while (enumeration.hasMoreElements())
            {
                ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor)enumeration.nextElement();
                try
                {
                    poolExecutor.awaitTermination(1L, TimeUnit.SECONDS);
                }
                catch (InterruptedException e) {}
            }
            pools.clear();
        }
        pools = null;
    }
}
