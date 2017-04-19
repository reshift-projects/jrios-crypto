package com.novatronic.cripto.service.perf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logp
{
    private static final Logger log = LoggerFactory.getLogger(Logp.class);
    private static final String SIMPLE_FORMAT = "{}|{}|{}|{}";
    private static final String TRX_FORMAT = "{}|{}|{}|{}|{}";

    public static void show(String process, long init)
    {
        long end = System.currentTimeMillis();
        long total = end - init;
        log.info("{}|{}|{}|{}", new Object[] { process, Long.valueOf(init), Long.valueOf(end), Long.valueOf(total) });
    }

    public static void showTrx(String trxId, String process, long init)
    {
        long end = System.currentTimeMillis();
        long total = end - init;
        log.info("{}|{}|{}|{}|{}", new Object[] { process, trxId, Long.valueOf(init), Long.valueOf(end), Long.valueOf(total) });
    }
}
