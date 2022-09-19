package com.github.ludmylla.foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface SendingEmailService {

    void send(Message message);

    @Builder
    @Getter
    class Message {
        private Set<String> addressee;
        private String topic;
        private String body;

    }
}
