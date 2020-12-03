package com.hackathon.practice.helper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class PetAPIHelper {
    private static final Logger logger = LogManager.getLogger(PetAPIHelper.class.getName());
    private final String BASE_URI = "https://petstore.swagger.io/v2/";

    public PetAPIHelper() {
        // To Do
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * To Add Pet
     *
     * @param requestBody Request Body Of Pet Details to be added
     * @return {@link Response}
     */
    public Response post(Object requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody.toString())
                .post("pet")
                .then()
                .extract()
                .response();
    }

    /**
     * To Update Pet Details using Request Body
     *
     * @param requestBody Request body of pet to be updated
     * @return {@link Response}
     */
    public Response update(Object requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody.toString())
                .put("pet")
                .then()
                .extract()
                .response();
    }

    /**
     * To get all pet details by Status
     *
     * @param petStatus Status of pet
     * @return {@link Response}
     */
    public Response getAllPetsByStatus(String petStatus) {
        return given()
                .queryParam("status", petStatus)
                .when()
                .get("/pet/findByStatus")
                .then()
                .extract()
                .response();
    }

    /**
     * To get pet by ID
     *
     * @param petID Id of pet to get
     * @return {@link Response}
     */
    public Response getPetById(Object petID) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pet/" + petID)
                .then()
                .extract()
                .response();
    }

    /**
     * To update pet by ID
     *
     * @param petID     ID of pet to update
     * @param petName   Name of pet to be updated with
     * @param petStatus Status of pet
     * @return {@link Response}
     */
    public Response updateByID(Object petID, String petName, String petStatus) {
        return given()
                .header("Content-type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .formParam("id", petID)
                .formParam("name", petName)
                .formParam("status", petStatus)
                .when()
                .post("/pet/" + petID)
                .then()
                .extract()
                .response();
    }

    /**
     * To delete a pet by ID
     *
     * @param petID Id of pet to delete
     * @return {@link Response}
     */
    public Response delete(Object petID) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/pet/" + petID)
                .then()
                .extract()
                .response();
    }
}
