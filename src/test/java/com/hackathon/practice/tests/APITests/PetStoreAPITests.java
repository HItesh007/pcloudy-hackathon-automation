package com.hackathon.practice.tests.APITests;

import com.github.javafaker.Faker;
import com.hackathon.pCloudy.json.JSONArray;
import com.hackathon.pCloudy.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PetStoreAPITests {
    private static final Logger logger = LogManager.getLogger(PetStoreAPITests.class.getName());
    private final String BASE_URI = "https://petstore.swagger.io/v2/";

    @Test(priority = 0)
    public void printAllPets() {
        RestAssured.baseURI = BASE_URI;

        Response response = given()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .extract()
                .response();

        if (response.getStatusCode() == 200) {
            // Print Details
            JSONArray petJsonArray = new JSONArray(response.asString());

            petJsonArray.forEach(petObject -> {
                JSONObject petJsonObject = (JSONObject) petObject;

                int petID = petJsonObject.getInt("id");
                String petName = petJsonObject.getString("name");

                logger.info("[" + petName + " | " + petID + "]");
            });
        } else {
            logger.error("Response Code Is : " + response.getStatusCode());
        }

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    public void crudOnPet() {
        RestAssured.baseURI = BASE_URI;

        // Prepare JSONObject of pet
        String animalName = new Faker().animal().name();
        logger.info("Animal To Add : " + animalName);

        JSONObject pet = new JSONObject();
        pet.put("name", animalName);
        pet.put("status", "Available");

        logger.info("JSON Body : " + pet);

        Response postResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .body(pet.toString())
                .post("/pet")
                .then()
                .extract()
                .response();

        Object petID = null;

        if (postResponse.getStatusCode() == 200) {
            // Pet added successfully. Get ID
            petID = new JSONObject(postResponse.asString()).get("id");
            String petName = new JSONObject(postResponse.asString()).getString("name");

            Assert.assertEquals(petName, animalName);
            logger.info("Response JSON : " + postResponse.asString());
        }

        // Update Pet
        String updatePetName = new Faker().animal().name();
        logger.info("Pet having id [" + petID + "] To be updated with : " + updatePetName);
        pet.put("name", updatePetName);
        pet.put("id", petID);

        // Correct PUT
        Response updatePost = given()
                .contentType(ContentType.JSON)
                .when()
                .body(pet.toString())
                .post("/pet/")
                .then()
                .extract()
                .response();

        // Pet added successfully. Get ID
        String petName = new JSONObject(updatePost.asString()).getString("name");
        logger.info("Updated JSON : " + updatePost.asString());
        Assert.assertEquals(petName, updatePetName);
    }
}
