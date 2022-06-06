package com.example.demo.controllers;

import com.example.demo.percistence.models.User;
import com.example.demo.services.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private UserService userService;

    @LocalServerPort
    int localServerPort;

    private MockMvc mockMvc;

    private User mockedUser() {
        return User.builder()
                .id(1)
                .username("maria")
                .build();
    }

    @BeforeEach
    public void setUp() {
         this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void findAllUsers() {

    }

    @Test
    void addUser() throws URISyntaxException {
        final URI uri = new URI("http://localhost:" + localServerPort + "/api/users");
        User user = mockedUser();

        org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
        HttpEntity<User> httpEntity = new HttpEntity(user, httpHeaders);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, httpEntity, String.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }
}