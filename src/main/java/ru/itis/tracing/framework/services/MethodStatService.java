package ru.itis.tracing.framework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.itis.tracing.framework.entities.Method;
import ru.itis.tracing.framework.repositories.MethodRepository;

import java.util.*;

@Service
@ConditionalOnProperty(name = "tracing", havingValue = "true")
public class MethodStatService {

    private final MethodRepository methodRepository;

    private final MicroservicesService microservicesService;

    @Autowired
    public MethodStatService(
            MethodRepository methodRepository,
            MicroservicesService microservicesService) {
        this.methodRepository = methodRepository;
        this.microservicesService = microservicesService;
    }

    public List<Method> findAll() {
        return methodRepository.findAll();
    }

    public void deleteAll() {
        methodRepository.deleteAll();
    }

    public List<Method> loadAllStats() {
        List<Method> methodList = methodRepository.findAll();

        List<Method> servicesStats = microservicesService.getAllMethodStatsFromServices();
        if(!servicesStats.isEmpty()) {
            methodRepository.saveAll(servicesStats);
            methodList.addAll(servicesStats);
        }
        return methodList;
    }

    public Optional<Method> findMethodByName(String methodName) {
        return methodRepository.findByName(methodName);
    }

    public void save(Method method) {
        methodRepository.save(method);
    }
}
