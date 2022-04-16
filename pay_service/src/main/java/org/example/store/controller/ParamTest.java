package org.example.store.controller;


import org.example.store.controller.web.ActionParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ParamTest {
    @PostMapping("/test-param")
    public String test(@RequestBody Map<String,String> data, ActionParam params){
        System.out.println(params);
        System.out.println(data);
        return "test";
    }
}
