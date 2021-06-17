package ru.itis.tracing.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracing.framework.entities.ClientService;
import ru.itis.tracing.framework.repositories.ClientTracingRepository;

@RestController
@ConditionalOnProperty(value = "tracing.main", havingValue = "true")
public class RegisterController {

    @Autowired
    private ClientTracingRepository clientTracingRepository;

    @PostMapping("/tracing/register")
    public ResponseEntity<Boolean> registerService(String url) {
        clientTracingRepository.save(new ClientService(url));
        return ResponseEntity.ok(true);
    }
}
