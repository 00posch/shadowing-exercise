package com.example.demo.controllers;

import com.example.demo.commands.UserDto;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int localServerPort;
    private MockMvc mockMvc;
    @MockBean
    protected UserRepository mockedUserRepository;

    private User userEntity() {
        return User.builder()
                .id(1)
                .username("manel")
                .build();
    }

    private User createUserAndAddItToRepository(User user) {
        return mockedUserRepository.save(user);
    }

    @BeforeEach
    public void setUp() {
         //this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void findAllUsers() {
        User user = userEntity();
        Mockito.when(mockedUserRepository.findAll()).thenReturn(List.of(user));
        this.testRestTemplate = new TestRestTemplate();
        String URI = "http://localhost:" + localServerPort + "/api/users";

        ResponseEntity<List<UserDto>> userResponseEntity = testRestTemplate.exchange (
                URI,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<UserDto>>() {}
        );
        assertEquals(200, userResponseEntity.getStatusCodeValue());
        assertNotNull(userResponseEntity.getBody());
        assertEquals(1, userResponseEntity.getBody().size());

        //TODO validate body
        //TODO exceptions
    }

    @Test
    void addUser() throws URISyntaxException {
        final URI uri = new URI("http://localhost:" + localServerPort + "/api/users");
        User user = userEntity();

        org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
        HttpEntity<User> httpEntity = new HttpEntity(user, httpHeaders);

        ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, httpEntity, String.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void getUser() {
        User user = userEntity();
        Mockito.when(mockedUserRepository.findById(1)).thenReturn(Optional.of(user));
        this.testRestTemplate = new TestRestTemplate();
        final String URI = "http://localhost:" + localServerPort + "/api/users/1";

        ResponseEntity<User> response = testRestTemplate.exchange (
                URI,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<User>() {}
        );

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void getUserShouldThrowUserNotFound() {User user = userEntity();
        Mockito.when(mockedUserRepository.findById(1)).thenReturn(Optional.empty());

        final String uri = "http://localhost:" + localServerPort + "/api/user/1";
        ResponseEntity<User> response = testRestTemplate.exchange (
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<User>() {});

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getUserShouldThrowUserNotFoundWhenRepositoryReturnsNull() {
        Mockito.when(mockedUserRepository.findById(1)).thenReturn(null);
        final String uri = "http://localhost:" + localServerPort + "/api/user/1";
        ResponseEntity<User> response = testRestTemplate.exchange (
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<User>() {});

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetUserAndCheckIfCacheIsWorking() {
        User user = userEntity();
        Mockito.when(mockedUserRepository.findById(1)).thenReturn(Optional.of(user));
        //Mockito.when(userService.getUser(1)).thenReturn(cacheManager.getCache(""));
        final String uri = "http://localhost:" + localServerPort + "/api/users/1";
        ResponseEntity<User> response = testRestTemplate.exchange (
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<User>() {}
        );

        ResponseEntity<User> response2 = testRestTemplate.exchange (
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<User>() {}
        );

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response2.getBody());
        assertEquals(200, response2.getStatusCodeValue());
        Mockito.verify(mockedUserRepository, Mockito.times(2)).findById(1);
    }

    @Test
    void updateUser() {
    }
}