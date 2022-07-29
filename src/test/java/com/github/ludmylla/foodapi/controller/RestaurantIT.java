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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantIT {

    private static final String VIOLATION_OF_BUSINESS_RULE_PROBLEM_TYPE = "Message incompressible";

    private static  final  String DATA_INVALID_PROBLEM_TITLE = "Data invalid.";

    private static final int RESTAURANT_ID_NON_EXISTENT= 100;

    @LocalServerPort
    private int port;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Restaurant restaurantMcdonald;

    private String jsonRestaurantCorrect;
    private String jsonRestaurantWithoutFreightRate;
    private String jsonRestaurantWithoutKitchen;
    private String jonRestaurantWithNonExistentKitchen;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        jsonRestaurantCorrect = ResourceUtils.getContentFromResource(
                "/json/correct/restaurant-new-york-barbecue.json");

        jonRestaurantWithNonExistentKitchen = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-with-kitchen-non-existent.json");

        jsonRestaurantWithoutKitchen = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-without-kitchen.json");

        jsonRestaurantWithoutFreightRate = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-without-freight-rate.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturnStatus201_whenToCreateRestaurant() {
            given()
                  .body(jsonRestaurantCorrect)
                  .contentType(ContentType.JSON)
                  .accept(ContentType.JSON)
            .when()
                   .post()
            .then()
                    .statusCode(201);
    }

    @Test
    public void shouldReturnStatus200_whenToConsultRestaurant() {
            given()
                  .accept(ContentType.JSON)
            .when()
                   .get()
            .then()
                   .statusCode(200);
    }

    @Test
    public void shouldReturnStatus404_whenToCreateRestaurantWithNonExistentKitchen() {
            given()
                  .body(jonRestaurantWithNonExistentKitchen)
                   .contentType(ContentType.JSON)
                   .accept(ContentType.JSON)
            .when()
                    .post()
            .then()
                    .statusCode(404)
                    .body("title", equalTo("Resource Not Found"));
    }

    @Test
    public void shouldReturnStatus400_whenToCreateRestaurantWithoutKitchen() {
            given()
                  .body(jsonRestaurantWithoutKitchen)
                  .contentType(ContentType.JSON)
                  .accept(ContentType.JSON)
            .when()
                   .post()
            .then()
                    .statusCode(400)
                    .body("title", equalTo("Invalid data."));
    }


    @Test
    public void shouldReturnStatus400_whenToCreateRestaurantWithoutFreightRate() {
            given()
                  .body(jsonRestaurantWithoutFreightRate)
                  .contentType(ContentType.JSON)
                  .accept(ContentType.JSON)
            .when()
                  .post()
            .then()
                   .statusCode(400)
                   .body("title", equalTo("Invalid data."));
    }

    @Test
    public void shouldReturnCorrectResponseAndStatus_WhenToConsultExistingRestaurant(){
            given()
                .pathParam("id", restaurantMcdonald.getId())
                 .accept(ContentType.JSON)
            .when()
                  .get("/{id}")
            .then()
                  .statusCode(200)
                   .body("name", equalTo(restaurantMcdonald.getName()));
    }

    @Test
    public void shouldReturnStatus404_WhenToConsultARestaurantThatDoesNotExist(){
            given()
                 .pathParam("id", RESTAURANT_ID_NON_EXISTENT)
                 .accept(ContentType.JSON)
            .when()
                  .get("/{id}")
            .then()
                   .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private void prepareData(){
        Kitchen kitchenFrench = new Kitchen();
        kitchenFrench.setName("French");
        kitchenRepository.save(kitchenFrench);

        Kitchen kitchenBrazilian = new Kitchen();
        kitchenBrazilian.setName("Brazilian");
        kitchenRepository.save(kitchenBrazilian);

        restaurantMcdonald = new Restaurant();
        restaurantMcdonald.setName("Mcdonald");
        restaurantMcdonald.setFreightRate(new BigDecimal(10));
        restaurantMcdonald.setKitchen(kitchenFrench);
        restaurantRepository.save(restaurantMcdonald);

        Restaurant restaurantFlavor = new Restaurant();
        restaurantFlavor.setName("Flavor");
        restaurantFlavor.setFreightRate(new BigDecimal(11));
        restaurantFlavor.setKitchen(kitchenBrazilian);
        restaurantRepository.save(restaurantFlavor);



    }

}
