//package com.redacode.redacode.service;
//import com.redacode.redacode.model.Professor;
//import com.redacode.redacode.model.UserProfAuthObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//
//@Service
//public class ProfessorService implements IProfessorService {
//    @Value("${redacode.redacode.login}")
//
//    private String studentName;
//    @Value("${redacode.redacode.password}")
//
//    private String studentPassword;
//
//    private String baseUrl = "http://172.16.16.247";
//
//    private String token;
//
//    private final RestTemplate restTemplate;
//
//    public ProfessorService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public UserProfAuthObject createAuthObject() {
//        return new UserProfAuthObject(studentName, studentPassword);
//    }
//
//    @Override
//    public String getToken(UserProfAuthObject profAuth) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<UserProfAuthObject> request = new HttpEntity<>(profAuth, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/auth/authenticate", request, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            token = response.getBody();
//            return token;
//        } else {
//            throw new RuntimeException("Authentication failed: " + response.getBody());
//        }
//    }
//
//    @Override
//    public List<Professor> getAll() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        HttpEntity<String> request = new HttpEntity<>(headers);
//
//        ResponseEntity<List<Professor>> response = restTemplate.exchange(baseUrl + "/professorService/get", HttpMethod.GET, request, new ParameterizedTypeReference<List<Professor>>() {});
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody();
//        } else {
//            throw new RuntimeException("Unable to get Professors: " + response.getBody());
//        }
//    }
//
//    @Override
//    public List<Professor> getById(List<String> professorIds) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        HttpEntity<List<String>> request = new HttpEntity<>(professorIds, headers);
//        ResponseEntity<List<Professor>> response = restTemplate.exchange(baseUrl + "/professorService/get/list", HttpMethod.GET, request, new ParameterizedTypeReference<List<Professor>>() {});
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody();
//        } else {
//            throw new RuntimeException("Unable to get Professors by id: " + response.getBody());
//        }
//    }
//}
