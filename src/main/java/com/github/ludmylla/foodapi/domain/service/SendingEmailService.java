package com.github.ludmylla.foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface SendingEmailService {

    void send(Message message);


    @Builder
    @Getter
    class Message {

        @Singular
        private Set<String> addressees;

        @NonNull
        private String topic;

        @NonNull
        private String body;

        @Singular("variable")
        private Map<String, Object> variables;

    }
}
