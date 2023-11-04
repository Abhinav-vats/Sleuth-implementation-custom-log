package com.example.sleuth.Client;

import jdk.internal.org.objectweb.asm.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/client")
public class ClientController {

    @Value("${api.url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public Map<String, Object> getDetailsClient(HttpServletRequest request){
        log.info("Entered: getDetailsClient");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authToken", request.getHeader("authToken"));
        MDC.put("X-AUTH-TOKEN", request.getHeader("authToken"));
        httpHeaders.add("authentication", request.getHeader("authentication"));
        ResponseEntity<Map<String, Object>> res = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(httpHeaders), new ParameterizedTypeReference<Map<String, Object>>() {});

        log.info("Exited: getDetailsClient");
        return res.getBody();
    }
}
