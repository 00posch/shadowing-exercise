package com.example.demo.configs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class ProfileManager {
    /*@Value("{spring.profiles.active}")
    private String profiles;*/
    private Logger logger = LoggerFactory.getLogger(ProfileManager.class);

    @Autowired
    private Environment environment;

    public void getProfiles() {
        for (String profile : environment.getActiveProfiles()) {
            logger.info("Current profile: " + profile);
        }
    }

}
