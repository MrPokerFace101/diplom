package ru.itis.tracing.framework.processors;

import java.util.ArrayList;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import ru.itis.tracing.framework.entities.Method;
import ru.itis.tracing.framework.entities.Stat;
import ru.itis.tracing.framework.notifications.NotificationStrategy;
import ru.itis.tracing.framework.services.MethodStatService;

@Aspect
@ConditionalOnBean(MethodStatService.class)
@Component
public class TraceAnnotationProcessor {

    @Autowired
    private MethodStatService methodStatService;

    @Autowired
    private NotificationStrategy notificationStrategy;

    @Value("tracing.notifications.coefficient")
    private Integer anomalyAmount;

    @Around("@annotation(ru.itis.tracing.framework.annotations.Trace)")
    public Object saveExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().getName();
        Optional<Method> methodOptional = methodStatService.findMethodByName(methodName);
        Method method;
        if(methodOptional.isPresent()) {
            method = methodOptional.get();
            Stat stat = createStat(start, executionTime);
            addStat(method, stat);
        } else {
            method = Method.builder()
                    .name(methodName)
                    .stats(
                            new ArrayList<Stat>(){{
                                add(createStat(start, executionTime));
                            }}
                    )
                    .averageExecutionTime((double) executionTime)
                    .build();
        }
        methodStatService.save(method);
        return proceed;
    }

    private Stat createStat(Long startTime, Long executionTime) {
        return Stat.builder().startTime(startTime).executionTime(executionTime).build();
    }

    private boolean checkForAnomalies(Method method, Stat stat) {
        return method.getAverageExecutionTime() < stat.getExecutionTime().doubleValue() * anomalyAmount;
    }

    private void addStat(Method method, Stat stat) {
        if (checkForAnomalies(method, stat)) {
            notificationStrategy.notify(
                    String.format(
                            "method %s worked for %d but usually works for %f. called at %d",
                            method.getName(),
                            stat.getExecutionTime(),
                            method.getAverageExecutionTime(),
                            stat.getStartTime()
                    )
            );
            method.addStat(stat);
        } else {
            method.addStatAndRecalculateAverageTime(stat);
        }
    }
}
