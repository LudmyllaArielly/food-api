package com.github.ludmylla.foodapi.infrastructure.service.email;

import com.github.ludmylla.foodapi.core.email.EmailProperties;
import com.github.ludmylla.foodapi.domain.service.SendingEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpSendingEmailServiceImpl implements SendingEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void send(Message message) {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(message.getAddressees().toArray(new String [0]));
            helper.setFrom(emailProperties.getSender());
            helper.setSubject(message.getTopic());
            helper.setText(message.getBody(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Could not send email.", e);
        }
    }
}
