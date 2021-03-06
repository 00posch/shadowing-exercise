package com.example.demo.configs;

import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner userRepositoryToMock(UserJpaRepository repository) {
        return args -> {
            User manel = new User(1, "manel");

            repository.saveAll(List.of(manel));
        };
    }

    /*@Bean
    CommandLineRunner cmdRunner2(UserRepo userRepo) {
        return args -> {
            User outroManel = new User(111, "outroManel");
            userRepo.saveAll(List.of(outroManel));
        };
    }*/
}
