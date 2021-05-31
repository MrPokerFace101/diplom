package ru.itis.services;

import ru.itis.tracing.framework.annotations.Trace;

import java.util.Random;

public class TestService {

    @Trace
    public String longMethod() {
        try {
            wait(new Random().nextInt(15000));
        } catch (InterruptedException e) {

        }
        return "done";
    }

    @Trace
    public String quickMethod() {
        return "done";
    }
}
