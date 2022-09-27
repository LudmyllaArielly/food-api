package com.github.ludmylla.foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Set;

public interface SendingEmailService {

    void send(Message message);


    @Builder
    @Getter
    class Message {

        @Singular
        private Set<String> addressees;

        private String topic;
        private String body;

    }
}
