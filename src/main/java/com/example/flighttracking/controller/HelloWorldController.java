package com.example.flighttracking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

        @GetMapping("/h")
    public String sayHello() {
        return "Hello, world!";
    }

}
