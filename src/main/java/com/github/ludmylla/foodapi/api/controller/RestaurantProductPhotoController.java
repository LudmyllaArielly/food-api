package com.github.ludmylla.foodapi.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestParam MultipartFile file){

        var nameFile = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        var filePhoto = Path.of("C:/Users/ludmy/Music/images", nameFile);

        System.out.println(filePhoto);
        System.out.println(file.getContentType());

        try {
            file.transferTo(filePhoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
