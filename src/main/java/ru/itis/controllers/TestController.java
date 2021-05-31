package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.services.TestService;

@RestController
public class TestController {

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    private final TestService testService;

    @GetMapping("/test-long")
    public String testLongMethod() {
        return testService.longMethod();
    }

    @GetMapping("test-short")
    public String testQuickMethod() {
        return testService.quickMethod();
    }
}
