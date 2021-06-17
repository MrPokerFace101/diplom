package ru.itis.tracing.framework.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.tracing.framework.exceptions.ServiceNotRegisteredException;

@Service
@ConditionalOnProperty(value = "tracing.main", havingValue = "false")
public class RegisterService {

    @Value("tracing.main.url")
    private String MAIN_SERVICE_URL;

    @Value("tracing.this.url")
    private String THIS_SERVICE_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    @EventListener(ApplicationReadyEvent.class)
    @Retryable(value = ServiceNotRegisteredException.class, maxAttempts = 2, backoff = @Backoff(delay = 30000))
    public void register() throws ServiceNotRegisteredException {
        ResponseEntity<Boolean> resultResponseEntity =
                restTemplate.postForEntity(
                        MAIN_SERVICE_URL + "/tracing/register",
                        THIS_SERVICE_URL,
                        Boolean.class
                );
        if(!resultResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ServiceNotRegisteredException(resultResponseEntity.getStatusCode() + " http code received, unable to register");
        }
    }
}
