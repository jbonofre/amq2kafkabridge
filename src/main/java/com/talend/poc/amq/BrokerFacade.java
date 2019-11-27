package com.talend.poc.amq;

import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.ipaas.rt.amq.security.TipaasSecurityPlugin;

public class BrokerFacade {

    private final static Logger LOGGER = LoggerFactory.getLogger(BrokerFacade.class);

    private BrokerService brokerService;

    public BrokerFacade(int port, String jettyConfig, long maxFrameSize, String syncopeAccess) throws Exception {
        LOGGER.info("Creating embedded ActiveMQ broker");
        brokerService = new BrokerService();
        brokerService.setBrokerName("facade");
        brokerService.setUseJmx(true);
        brokerService.setPersistenceAdapter(new MemoryPersistenceAdapter());
        if (jettyConfig != null) {
            brokerService.addConnector("http://0.0.0.0:" + port + "?jetty.config=" + jettyConfig + "&wireFormat.maxFrameSize=" + maxFrameSize);
        } else {
            brokerService.addConnector("http://0.0.0.0:" + port + "?wireFormat.maxFrameSize=" + maxFrameSize);
        }
        brokerService.addConnector("vm://facade");
        brokerService.setPopulateJMSXUserID(true);
        brokerService.setUseAuthenticatedPrincipalForJMSXUserID(false);

        SystemUsage systemUsage = new SystemUsage();
        MemoryUsage memoryUsage = new MemoryUsage();
        memoryUsage.setPercentOfJvmHeap(80);
        systemUsage.setMemoryUsage(memoryUsage);
        StoreUsage storeUsage = new StoreUsage();
        storeUsage.setLimit(419430400);
        systemUsage.setStoreUsage(storeUsage);
        brokerService.setSystemUsage(systemUsage);

        TipaasSecurityPlugin plugin = new TipaasSecurityPlugin();
        plugin.setActivemqSecurityURL(syncopeAccess);
        BrokerPlugin[] plugins = new BrokerPlugin[]{ plugin };
        brokerService.setPlugins(plugins);
    }

    public void start() throws Exception {
        LOGGER.info("Starting ActiveMQ embedded broker");
        brokerService.start();
    }

    public void stop() throws Exception {
        LOGGER.info("Stopping ActiveMQ embedded broker");
        brokerService.stop();
    }
}
