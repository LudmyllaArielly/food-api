package com.github.ludmylla.foodapi.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSendEmailService extends SmtpSendingEmailServiceImpl {

    @Override
    public void send(Message message) {
        String body = processTemplate(message);

        log.info("[FAKE -E-MAIL]Para: {}\n{}", message.getVariables(), body);
    }
}
