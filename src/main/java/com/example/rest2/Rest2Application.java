package com.example.rest2;

import com.example.rest2.Entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Rest2Application {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<User> entity;
    private HttpHeaders headers = new HttpHeaders();
    private static final String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        SpringApplication.run(Rest2Application.class, args);

        Rest2Application restApplication = new Rest2Application();
        restApplication.getAllUsers();
        restApplication.createUser(new User(3, "James", "Brown", (byte) 23));
        restApplication.updateUser(new User(3, "Thomas", "Shelby", (byte) 23));
        restApplication.deleteUser(3);
    }

    public void getAllUsers() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        entity = new HttpEntity<>(headers);
        System.out.println(responseEntity.getBody());
    }

    public void createUser(User user) {
        entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println(responseEntity.getBody());
    }

    public void updateUser(User user) {
        entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println(responseEntity.getBody());
    }

    public void deleteUser(long id) {
        entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}

