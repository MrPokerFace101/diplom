package ru.itis.tracing.framework.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.tracing.framework.entities.Method;
import ru.itis.tracing.framework.repositories.ClientTracingRepository;

@Service
@ConditionalOnProperty(value = "tracing.main", havingValue = "true")
public class MicroservicesService {

    private final ClientTracingRepository clientTracingRepository;

    public List<Method> getAllMethodStatsFromServices() {
        RestTemplate restTemplate = new RestTemplate();
        return clientTracingRepository
                .findAll()
                .stream()
                .map(client -> {
                    ResponseEntity<Method[]> resultResponseEntity =
                            restTemplate.getForEntity(client.getUrl() + "/method-stats/request", Method[].class);
                    if (resultResponseEntity.getStatusCode().is2xxSuccessful()) {
                        Method[] result = resultResponseEntity.getBody();
                        if (result != null && result.length != 0) {
                            return Arrays.asList(result);
                        } else {
                            return new ArrayList<Method>();
                        }
                    } else {
                        return new ArrayList<Method>();
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public MicroservicesService(ClientTracingRepository clientTracingRepository) {
        this.clientTracingRepository = clientTracingRepository;
    }
}
