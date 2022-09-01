package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.ProductInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.ProductModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.ProductModel;
import com.github.ludmylla.foodapi.domain.dtos.input.ProductInputModel;
import com.github.ludmylla.foodapi.domain.model.Product;
import com.github.ludmylla.foodapi.domain.service.ProductService;
import com.github.ludmylla.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductModelAssembler productModelAssembler;

    @Autowired
    private ProductInputDisassembler productInputDisassembler;

    @PostMapping
    public ResponseEntity<ProductModel> create(@PathVariable Long restaurantId, @RequestBody @Valid ProductInputModel productInput) {
        Product product = productInputDisassembler.toDomainModel(productInput);
        product = productService.create(restaurantId, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productModelAssembler.toModel(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> findAllByRestaurant(@PathVariable Long restaurantId, @RequestParam(required = false) boolean includeInactive) {
        List<Product> list = productService.findAllProductByRestaurant(restaurantId, includeInactive);
        return ResponseEntity.ok(productModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
        Product product = productService.findByIdRestaurant(restaurantId, productId);
        return ResponseEntity.ok(productModelAssembler.toModel(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductModel> update(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInputModel productInput) {
        Product product = productInputDisassembler.toDomainModel(productInput);
        product = productService.update(restaurantId, productId, product);
        return ResponseEntity.ok(productModelAssembler.toModel(product));
    }
}
