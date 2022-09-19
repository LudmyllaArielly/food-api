package com.github.ludmylla.foodapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.ludmylla.foodapi.core.storage.StorageProperties;
import com.github.ludmylla.foodapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void store(NewPhoto newPhoto) {
        try {

            var filePath = getFilePath(newPhoto.getFileName());

            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(newPhoto.getContentType());


            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead);


            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Unable to upload file to Amazon s3.", e);
        }
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), fileName);
    }

    @Override
    public void remove(String fileName) {
        String filePath = getFilePath(fileName);

        var deleteObjectRequest = new DeleteObjectRequest(
                storageProperties.getS3().getBucket(), filePath);

        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Override
    public InputStream toRestore(String fileName) {
        return null;
    }
}
