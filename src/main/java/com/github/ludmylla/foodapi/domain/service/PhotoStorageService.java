package com.github.ludmylla.foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    void store(NewPhoto newPhoto);

    void remove(String fileName);

    InputStream toRestore(String fileName);

    default void replace(String fileNameExisting, NewPhoto newPhoto){
        this.store(newPhoto);

        if(fileNameExisting != null){
            this.remove(fileNameExisting);
        }
    }

    default String generateFileName(String nameOrigin){
        return UUID.randomUUID().toString() + "_" + nameOrigin;
    }

    @Builder
    @Getter
    class NewPhoto {
        private String fileName;
        private InputStream inputStream;
    }
}
