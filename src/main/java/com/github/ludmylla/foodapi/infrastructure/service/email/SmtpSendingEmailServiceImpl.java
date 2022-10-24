package com.github.ludmylla.foodapi.infrastructure.service.email;

import com.github.ludmylla.foodapi.core.email.EmailProperties;
import com.github.ludmylla.foodapi.domain.service.SendingEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SmtpSendingEmailServiceImpl implements SendingEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = createMimeMessage(message);
            mailSender.send(mimeMessage);
        }catch (Exception ex){
            throw new EmailException("Could not send email.", ex);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {

        String body = processTemplate(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getSender());
        helper.setTo(message.getAddressees().toArray(new String[0]));
        helper.setSubject(message.getTopic());
        helper.setText(body, true);

        return mimeMessage;
    }

    public String processTemplate(Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());

        }catch (Exception ex) {
            throw new EmailException("Could not mount email template.", ex);
        }
    }
}
