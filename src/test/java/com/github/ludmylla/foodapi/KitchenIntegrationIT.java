package com.github.ludmylla.foodapi;

import com.github.ludmylla.foodapi.domain.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.service.KitchenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KitchenIntegrationIT {

    @Autowired
    private KitchenService kitchenService;

    @Test
    public void shouldCreateKitchenSuccessfully(){
        // scenery
        Kitchen newKitchen = new Kitchen();
        newKitchen.setName("Chinese");

        // actions
        newKitchen = kitchenService.create(newKitchen);

        // validations
        Assertions.assertNotNull(newKitchen);
        Assertions.assertNotNull(newKitchen.getId());
    }

    @Test
    public void shouldFailToCreateUnnamedKitchen() {

        Kitchen newKitchen = new Kitchen();
        newKitchen.setName(null);

        ConstraintViolationException error =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                     kitchenService.create(newKitchen);
                });
        Assertions.assertNotNull(error);
    }

    @Test
    public void shouldFailWhenToDeleteKitchenInUse() {

        EntityInUseException error =
                Assertions.assertThrows(EntityInUseException.class, () -> {
                    kitchenService.delete(1L);
                });
    }

    @Test
    public void shouldFailWhenToDeleteNonExistentKitchen(){

        EntityNotFoundException error =
                Assertions.assertThrows(EntityNotFoundException.class, () -> {
                    kitchenService.delete(100L);
                });
    }

}
