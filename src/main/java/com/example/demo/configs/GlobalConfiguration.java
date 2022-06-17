package com.example.demo.configs;

import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserRepositoryDBImpl;
import com.example.demo.percistence.repository.UserRepositoryStaticImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

@Configuration
public class GlobalConfiguration {
    private ConfigurableEnvironment environment;
    private List<User> usersList;

    /*@Bean
    @ConditionalOnProperty(name = "spring.config.activate.on-profile", havingValue = "production")
    public UserRepositoryDBImpl getUserRepositoryDBImpl() {
        return new UserRepositoryDBImpl();
    }*/

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.activate", havingValue = "qa")
    public UserRepositoryStaticImpl getUserRepositoryStaticImpl() {
        return new UserRepositoryStaticImpl(usersList);
    }

}
