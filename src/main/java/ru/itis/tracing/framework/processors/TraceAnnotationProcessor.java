package ru.itis.tracing.framework.processors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import ru.itis.tracing.framework.MethodStatService;
import ru.itis.tracing.framework.entities.MethodStat;

@Aspect
@ConditionalOnProperty(name = "tracing", value = "true")
public class TraceAnnotationProcessor {

    private final MethodStatService methodStatService;

    public TraceAnnotationProcessor(MethodStatService methodStatService) {
        this.methodStatService = methodStatService;
    }

    @Around("@annotation(Trace)")
    public Object saveExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        MethodStat methodStat = MethodStat.builder()
                .methodName(joinPoint.getSignature().getName())
                .executionTime(executionTime)
                .timestamp(start)
                .build();
        methodStatService.saveStat(methodStat);
        return proceed;
    }
}
