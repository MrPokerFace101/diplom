package ru.itis.tracing.framework.notifications;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("tracing.notifications.email")
public class EmailNotificatior implements Notifier {

    @Value("tracing.notifications.email")
    private String receiverEmail;

    @Value("tracing.notifications.email.username")
    private String notifierUsername;
    @Value("tracing.notifications.email.password")
    private String notifierPassword;

    private final JavaMailSenderImpl javaMailSender;

    @Override
    public void sendNotification(String msg) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setFrom(notifierUsername);
        simpleMailMessage.setSubject("smth went wrong in your system");
        javaMailSender.send(simpleMailMessage);
    }

    public EmailNotificatior() {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername(notifierUsername);
        javaMailSender.setPassword(notifierPassword);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }
}
