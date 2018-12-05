package com.example.idkw.issue894;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService kafkaService) {
        this.exampleService = kafkaService;
    }

    @PostMapping("example")
    public void sendExampleEvent(){
        exampleService.sendRandom();
    }
}
