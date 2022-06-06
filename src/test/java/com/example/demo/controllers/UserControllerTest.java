package com.example.demo.controllers;

import com.example.demo.percistence.models.User;
import com.example.demo.services.UserService;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    private UserService userService;


    @LocalServerPort
    private int localServerPort;

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
    void findAllUsers() { //TODO ask why using RestTemplate and TestRestTemplate at the same time!
        this.testRestTemplate = new TestRestTemplate();
        String URI = "http://localhost:" + localServerPort + "/api/users";
        ResponseEntity<String> userResponseEntity = testRestTemplate.getForEntity(URI, String.class);
        //assertEquals(200, userResponseEntity.getStatusCodeValue());
    }

    @Test
    void addUser() throws URISyntaxException {
        final URI uri = new URI("http://localhost:" + localServerPort + "/api/users");
        User user = mockedUser();

        org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
        HttpEntity<User> httpEntity = new HttpEntity(user, httpHeaders);

        ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, httpEntity, String.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void getUser() {
        User user = mockedUser();
        this.testRestTemplate = new TestRestTemplate();

        final String URI = "http://localhost:" + localServerPort + "/api/users";

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(URI, String.class);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    @Test
    void updateUser() {
    }
}