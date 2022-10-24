package com.github.ludmylla.foodapi.core.email;

import com.github.ludmylla.foodapi.domain.service.SendingEmailService;
import com.github.ludmylla.foodapi.infrastructure.service.email.FakeSendEmailService;
import com.github.ludmylla.foodapi.infrastructure.service.email.SandboxSendEmailService;
import com.github.ludmylla.foodapi.infrastructure.service.email.SmtpSendingEmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    public SendingEmailService sendingEmailService(){
        switch (emailProperties.getImpl()) {

            case SANDBOX: return new SandboxSendEmailService();

            case FAKE: return new FakeSendEmailService();

            case SMTP: return new SmtpSendingEmailServiceImpl();

            default: return null;
        }
    }
}
