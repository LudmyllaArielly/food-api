package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.PhotoProductModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.PhotoProductModel;
import com.github.ludmylla.foodapi.domain.dtos.input.PhotoProductInput;
import com.github.ludmylla.foodapi.domain.model.PhotoProduct;
import com.github.ludmylla.foodapi.domain.model.Product;
import com.github.ludmylla.foodapi.domain.service.PhotoProductService;
import com.github.ludmylla.foodapi.domain.service.PhotoStorageService;
import com.github.ludmylla.foodapi.domain.service.ProductService;
import com.github.ludmylla.foodapi.domain.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @Autowired
    private PhotoProductService photoProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PhotoProductModelAssembler photoProductModelAssembler;

    @Autowired
    private PhotoStorageService photoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoProductModel updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                         MultipartFile file, @Valid PhotoProductInput photoProductInput) throws IOException {

        Product product = productService.findByIdRestaurant(restaurantId, productId);
        MultipartFile multipartFile = photoProductInput.getFile();

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setDescription(photoProductInput.getDescription());
        photo.setContentType(multipartFile.getContentType());
        photo.setSize(multipartFile.getSize());
        photo.setFileName(multipartFile.getOriginalFilename());

        PhotoProduct photoSave = photoProductService.save(photo, file.getInputStream());

        return photoProductModelAssembler.toModel(photoSave);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoProductModel> findPhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
        PhotoProduct photoProduct = photoProductService.findById(restaurantId, productId);
        return ResponseEntity.ok(photoProductModelAssembler.toModel(photoProduct));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> showPhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                                         @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            PhotoProduct photoProduct = photoProductService.findById(restaurantId, productId);

            MediaType mediaTypePhoto = MediaType.parseMediaType(photoProduct.getContentType());
            List<MediaType> mediaTypesAccepted = MediaType.parseMediaTypes(acceptHeader);
            checkCompatibilityMediaType(mediaTypePhoto, mediaTypesAccepted);

            InputStream inputStream = photoStorageService.toRestore(photoProduct.getFileName());

            return ResponseEntity.ok()
                    .contentType(mediaTypePhoto)
                    .body(new InputStreamResource(inputStream));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void checkCompatibilityMediaType(MediaType mediaTypePhoto, List<MediaType> mediaTypesAccepted) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = mediaTypesAccepted.stream()
                .anyMatch(mediaTypeAccepted -> mediaTypeAccepted.isCompatibleWith(mediaTypePhoto));

        if(!compatible) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAccepted);
        }
    }

}
