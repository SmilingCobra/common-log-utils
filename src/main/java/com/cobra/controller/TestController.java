package com.cobra.controller;

import com.cobra.aspect.LogEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @LogEndpoint(module = "log")
    @RequestMapping("/log")
    public String log(){
        return "success";
    }

}
