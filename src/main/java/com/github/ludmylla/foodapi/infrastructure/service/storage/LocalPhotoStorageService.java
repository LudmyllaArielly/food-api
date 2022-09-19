package com.github.ludmylla.foodapi.infrastructure.service.storage;

import com.github.ludmylla.foodapi.core.storage.StorageProperties;
import com.github.ludmylla.foodapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

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

    @Override
    public PhotoRestore toRestore(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            PhotoRestore photoRestore = PhotoRestore.builder()
                    .inputStream(Files.newInputStream(filePath))
                    .build();


            return photoRestore;
        } catch (Exception e) {
            throw new StorageException("Unable to recover the file.", e);
        }
    }

    private Path getFilePath(String fileName) {
        return storageProperties.getLocal().getFolderPhotos().resolve(Path.of(fileName));
    }
}
