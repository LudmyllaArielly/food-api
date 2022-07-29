package com.github.ludmylla.foodapi.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class KitchenServiceTest {


    @Test
    public void shouldReturnSuccess_WhenToCreateKitchen(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();


    }

    @Test
    public void shouldReturnSuccess_WhenToConsultKitchen(){

    }

    @Test
    public void shouldReturnFailure_whenToConsultAKitchenThatDoesNotExist (){

    }

}
