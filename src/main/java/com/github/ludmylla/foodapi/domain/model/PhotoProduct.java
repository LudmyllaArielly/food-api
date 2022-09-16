package com.github.ludmylla.foodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class PhotoProduct {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    @Column(name = "name_file")
    private String fileName;

    private String description;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long size;

    public Long getRestaurantId(){
        if(getProduct() != null){
            return getProduct().getRestaurant().getId();
        }

        return null;
    }

}
