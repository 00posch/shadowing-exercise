package com.example.demo.configs;

import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserRepositoryStaticImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.slf4j.Logger;

import java.util.List;

@Configuration
public class GlobalConfiguration {
    private ConfigurableEnvironment environment;
    private List<User> usersList;
    private Logger logger;

    /*@Bean
    @ConditionalOnProperty(name = "spring.config.activate.on-profile", havingValue = "production")
    public UserRepositoryDBImpl getUserRepositoryDBImpl() {
        return new UserRepositoryDBImpl();
    }*/

    /*@Bean
    @ConditionalOnProperty(name = "spring.profiles.activate", havingValue = "qa")
    public UserRepositoryStaticImpl getUserRepositoryStaticImpl() {
        return new UserRepositoryStaticImpl(usersList);
    }*/

    /*@Bean
    @Profile("qa")
    public CommandLineRunner demo(UserRepositoryStaticImpl mockedRepository) {
        return (args) -> {
            logger.info("Customers found with findAll():");logger.info("-------------------------------");
            for (User user: mockedRepository.getAllUsers()) {
                logger.info(user.toString());
            }
            logger.info("");
            // fetch an individual customer by ID
            logger.info("Customer 1 " + mockedRepository.getUserById(1).toString());
        };
    }*/

}
