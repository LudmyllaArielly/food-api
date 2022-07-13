package com.github.ludmylla.foodapi.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("/resource-not-found","Resource Not Found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use."),
    ERROR_BUSINESS("/error-business", "Error business."),
    MESSAGE_INCOMPRESSIBLE("/message-incompressible.", "Message incompressible"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter."),
    SYSTEM_ERROR("/system-error","System error."),
    INVALID_DATA("/invalid-data","Invalid data.");


    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://ludmylla.com.br" + path;
        this.title = title;
    }
}
