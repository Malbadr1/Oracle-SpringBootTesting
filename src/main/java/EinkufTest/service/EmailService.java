package EinkufTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTestReport(String report) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("Albadri@gmx.net");
        message.setSubject("📊 Report");
        message.setText(report);

        mailSender.send(message);
    }
}