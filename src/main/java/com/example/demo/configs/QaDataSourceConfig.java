package com.example.demo.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("qa")
public class QaDataSourceConfig implements DatasourceConfig{
    private Logger logger = LoggerFactory.getLogger(QaDataSourceConfig.class);


    @Override
    public void setUp() {
        logger.info("Setting up datasource for QA env...");
    }
}
