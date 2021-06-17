package ru.itis.tracing.framework.notifications;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationStrategy {

    private final List<Notifier> notifiers;

    public void notify(String msg) {
        notifiers.forEach(notifier -> notifier.sendNotification(msg));
    }

    @Autowired
    public NotificationStrategy(List<Notifier> notifiers) {
        this.notifiers = notifiers;
    }
}
