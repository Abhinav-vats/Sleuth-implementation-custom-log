package com.example.sleuth.Server;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/server")
public class ServerController {

    @GetMapping
    public Map<String, Object> getDetails(HttpServletRequest request){
        log.info("Entered : getDetails");
        MDC.put("X-AUTH-TOKEN", request.getHeader("authToken"));
        Map<String, Object> map = new TreeMap<>();
        List<String> cloths = Arrays.asList("T-Shirt", "Shirt", "Pant", "Lungi", "Towel", "Track-suit");
        map.put("cloths", cloths);
        map.put("name", "Abhinav");
        map.put("dob", "19-11-1997");
        map.put("State", "Uttar Pradesh");
        log.info("Exited : getDetails");
        return map;
    }

}
