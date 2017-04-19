package com.novatronic.cripto.service.config;

import com.novatronic.cripto.resource.ResourceHelper;
import java.util.Properties;

public class Configuration
{
    protected final String brokerName;
    protected final int timeForThreads;
    protected final int brokerInterval;
    protected final int numThreads;
    protected final String serverName;
    protected final int port;
    protected Properties config;

    public Configuration(String configname)
    {
        this(ResourceHelper.findAsProperties(configname));
    }

    public Configuration(Properties config)
    {
        this.config = config;
        this.brokerName = config.getProperty("BrokerName");
        this.timeForThreads = Integer.parseInt(config.getProperty("BrokerTimeToBusyThreads"));
        this.brokerInterval = Integer.parseInt(config.getProperty("BrokerInterval"));

        this.numThreads = Integer.parseInt(config.getProperty("ControllerThreads", "1"));
        this.serverName = config.getProperty("SIXServer", "server");
        this.port = Integer.parseInt(config.getProperty("BrokerSocketPort", "8888"));
    }

    public String getBrokerName()
    {
        return this.brokerName;
    }

    public int getTimeForThreads()
    {
        return this.timeForThreads;
    }

    public int getBrokerInterval()
    {
        return this.brokerInterval;
    }

    public int getNumThreads()
    {
        return this.numThreads;
    }

    public String getServerName()
    {
        return this.serverName;
    }

    public int getPort()
    {
        return this.port;
    }

    public String getProperty(String key)
    {
        return this.config.getProperty(key);
    }

    public Properties getConfig()
    {
        return this.config;
    }

    public String toString()
    {
        return "Configuration{brokerName=" + this.brokerName + ", timeForThreads=" + this.timeForThreads + ", brokerInterval=" + this.brokerInterval + ", numThreads=" + this.numThreads + ", serverName=" + this.serverName + '}';
    }
}
