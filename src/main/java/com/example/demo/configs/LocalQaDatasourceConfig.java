package com.example.demo.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalQaDatasourceConfig implements DatasourceConfig {
    private Logger logger = LoggerFactory.getLogger(ProfileManager.class);

    @Override
    public void setUp() {
        logger.info("Setting up datasource for LOCAL-QA env...");
    }
}
