package com.github.ludmylla.foodapi.infrastructure.service.storage;

import com.github.ludmylla.foodapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    @Value("${food.api.storage.local.photos}")
    private Path localPhotos;

    @Override
    public void store(NewPhoto newPhoto) {
        try {
            Path filePath = getFilePath(newPhoto.getFileName());

            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));

        } catch (Exception e) {
            throw new StorageException("Could not store photo.", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Could not delete file.", e);
        }
    }

    private Path getFilePath(String fileName) {
        return localPhotos.resolve(Path.of(fileName));
    }
}
