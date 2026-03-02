package com.example.temperatureAI.controller;

import com.example.temperatureAI.model.SimpleQuery;
import com.example.temperatureAI.model.SimpleQueryResponse;
import com.example.temperatureAI.service.SpringAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringAiController {
    private final SpringAIService springAIService;

    public SpringAiController(SpringAIService springAIService) {
        this.springAIService = springAIService;
    }

    @PostMapping("/simpleQuery")
    public ResponseEntity<SimpleQueryResponse> simpleQuery(@RequestBody SimpleQuery simpleQuery) {

        SimpleQueryResponse response = springAIService.simpleQuery(simpleQuery.query());
        System.out.println(response);
        return ResponseEntity.ok(response);

    }

}
