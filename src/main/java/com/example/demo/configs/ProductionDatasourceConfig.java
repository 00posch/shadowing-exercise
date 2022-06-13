package com.example.demo.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component // why not Configurable/Configuration
@Profile("production")
public class ProductionDatasourceConfig implements DatasourceConfig {
    private Logger logger = LoggerFactory.getLogger(ProfileManager.class);

    @Override
    public void setUp() {
        logger.info("Setting up datasource for Production env...");
    }
}
