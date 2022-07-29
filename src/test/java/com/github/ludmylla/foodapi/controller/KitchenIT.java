package com.github.ludmylla.foodapi.controller;

import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.repository.KitchenRepository;
import com.github.ludmylla.foodapi.domain.repository.RestaurantRepository;
import com.github.ludmylla.foodapi.util.DatabaseCleaner;
import com.github.ludmylla.foodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenIT {

    private static final int KITCHEN_ID_NON_EXISTENT = 100;
    private String jsonCorrectKitchenChinese;

    private int quantityCreateKitchens;

    @LocalServerPort
    private int port;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Kitchen kitchenWithoutRestaurant;
    private Kitchen kitchenWithRestaurant;

    private Restaurant restaurantWithKitchen;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        jsonCorrectKitchenChinese = ResourceUtils.getContentFromResource(
                "/json/correct/kitchen-chinese.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturnStatus200_whenToConsultKitchens(){

            given()
                .accept(ContentType.JSON)
                    .when().get().then().statusCode(200);
    }

    @Test
    public void shouldReturnCorrectQuantityKitchens_whenToConsultKitchens(){
            given()
                .accept(ContentType.JSON)
                    .when().get().then()
                        .body("", hasSize(quantityCreateKitchens));
    }

    @Test
    public void shouldReturnStatus201_whenToCreateKitchen() {
            given()
                 .body(jsonCorrectKitchenChinese)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                        .when().post().then().statusCode(201);
    }

    @Test
    public void shouldReturnStatus200_whenToConsultExistingKitchen(){
            given()
                 .pathParam("id",2)
                 .accept(ContentType.JSON)
            .when()
                 .get("/{id}")
            .then()
                  .statusCode(200)
                  .body("name", equalTo("Brazilian"));
    }

    @Test
    public void shouldReturnStatus404_whenToConsultNonExistentKitchen(){
            given()
                    .pathParam("id",KITCHEN_ID_NON_EXISTENT)
                    .accept(ContentType.JSON)
                    .when()
                    .get("/{id}")
                    .then()
                    .statusCode(404);
    }

    @Test
    public void shouldReturnStatus204_whenToDeleteAKitchenThatDoesHaveARestaurant(){
            given()
                  .pathParam("id", kitchenWithoutRestaurant.getId())
                  .accept(ContentType.JSON)
            .when()
                   .delete("/{id}")
            .then()
                   .statusCode(204);
    }

    @Test
    public void shouldReturnStatus409_whenToDeleteAKitchenThatARestaurant(){
            given()
                  .pathParam("id", kitchenWithRestaurant.getId())
                  .accept(ContentType.JSON)
            .when()
                  .delete("/{id}")
            .then()
                   .statusCode(409);
    }

    private void prepareData() {

        Kitchen kitchenFrench = new Kitchen();
        kitchenFrench.setName("French");
        kitchenRepository.save(kitchenFrench);

        Kitchen kitchenBrazilian = new Kitchen();
        kitchenBrazilian.setName("Brazilian");
        kitchenRepository.save(kitchenBrazilian);

        kitchenWithoutRestaurant = new Kitchen();
        kitchenWithoutRestaurant.setName("Kitchen without restaurant");
        kitchenRepository.save(kitchenWithoutRestaurant);

        kitchenWithRestaurant = new Kitchen();
        kitchenWithRestaurant.setName("Kitchen with restaurant");
        kitchenRepository.save(kitchenWithRestaurant);

        restaurantWithKitchen = new Restaurant();
        restaurantWithKitchen.setName("Restaurant with kitchen");
        restaurantWithKitchen.setFreightRate(new BigDecimal(10));
        restaurantWithKitchen.setKitchen(kitchenWithRestaurant);
        restaurantRepository.save(restaurantWithKitchen);

        quantityCreateKitchens = (int) kitchenRepository.count();

    }

}
