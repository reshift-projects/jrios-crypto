package com.novatronic.cripto.service.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseThreadFactory
        implements ThreadFactory
{
    private final ThreadGroup group;
    private final AtomicInteger threadTrace;
    private final StringBuilder prefix;

    public BaseThreadFactory(String poolName, int poolTrace)
    {
        this.threadTrace = new AtomicInteger(1);
        this.group = new ThreadGroup(poolName);
        this.prefix = new StringBuilder(poolName);
        this.prefix.append("-").append(poolTrace).append("-th-");
    }

    public String getName(int trace)
    {
        return this.prefix.toString() + trace;
    }

    public Thread newThread(Runnable runable)
    {
        Thread t = new Thread(this.group, runable, getName(this.threadTrace.getAndIncrement()), 0L);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }
}
