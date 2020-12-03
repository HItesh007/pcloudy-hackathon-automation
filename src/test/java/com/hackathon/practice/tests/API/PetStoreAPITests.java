package com.hackathon.practice.tests.API;

import com.github.javafaker.Faker;
import com.hackathon.pCloudy.json.JSONArray;
import com.hackathon.pCloudy.json.JSONObject;
import com.hackathon.practice.faker.PetFaker;
import com.hackathon.practice.helper.PetAPIHelper;
import io.restassured.response.Response;
import org.apache.commons.lang.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PetStoreAPITests {
    private static final Logger logger = LogManager.getLogger(PetStoreAPITests.class.getName());

    @Test(priority = 0)
    public void printAllPets() {
        Response response = new PetAPIHelper()
                .getAllPetsByStatus("available");

        if (response.getStatusCode() == 200) {
            // Print Details
            JSONArray petJsonArray = new JSONArray(response.asString());

            petJsonArray.forEach(petObject -> {
                JSONObject petJsonObject = (JSONObject) petObject;

                Object petID = petJsonObject.get("id");
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
        // Create object of PetAPIHelper
        PetAPIHelper petAPIHelper = new PetAPIHelper();

        // Generate Random Pet Name
        String animalName = WordUtils.capitalizeFully(PetFaker.getPet());
        logger.info("Pet To Be Added : " + animalName);

        // Prepare Pet's Request Object
        JSONObject pet = new JSONObject();
        pet.put("name", animalName);
        pet.put("status", "Available");

        // Prepare json Array of photo url
        JSONArray photoUrlJArray = new JSONArray();
        photoUrlJArray.put(PetFaker.petUrl());

        // Add Photo url to jsonObject
        pet.put("photoUrls", photoUrlJArray);

        // Print Json Object
        logger.info("JSON Body : " + pet);

        // Post Pet Details Using API
        Response postResponse = petAPIHelper.post(pet);

        Object petID = null;

        // If Added, print pet id
        if (postResponse.getStatusCode() == 200) {
            // Pet added successfully. Get ID
            petID = new JSONObject(postResponse.asString()).get("id");
            String petName = new JSONObject(postResponse.asString()).getString("name");

            Assert.assertEquals(petName, animalName);
            logger.info("Response JSON : " + postResponse.asString());
        }

        // Update Pet
        String updatedPetName = WordUtils.capitalizeFully(new Faker().animal().name());
        logger.info("Pet having id [" + petID + "] To be updated with : " + updatedPetName);
        pet.put("name", updatedPetName);
        pet.put("id", petID);

        // Update Created Pet Details
        Response updateResponse = petAPIHelper.update(pet);

        // Pet added successfully. Get ID
        String petName = new JSONObject(updateResponse.asString()).getString("name");
        logger.info("Updated JSON : " + updateResponse.asString());
        Assert.assertEquals(petName, updatedPetName);

        // Update Pet By ID
        Response updatedPet = petAPIHelper.updateByID(petID, updatedPetName, "sold");

        // Assert Status code
        Assert.assertEquals(updatedPet.getStatusCode(), 200);

        // Get pet by id and assert status
        Response petByIDResponse = petAPIHelper.getPetById(petID);

        // Print Updated Json
        logger.info("Updated JSON-2 : " + petByIDResponse.asString());

        // Assert Pet Status
        Assert.assertEquals(new JSONObject(petByIDResponse.asString()).getString("status"), "sold");

        // Delete Pet And Assert deletion success
        Assert.assertEquals(petAPIHelper.delete(petID).getStatusCode(), 200);

        // Check if pet exists or not
        // Assert that the pet is not found
        Assert.assertEquals(petAPIHelper.getPetById(petID).getStatusCode(), 404);
    }

    @Test(priority = 2)
    public void updateAllAvailablePets() {
        // Create object of API Helper
        PetAPIHelper petAPIHelper = new PetAPIHelper();

        // Get all available pets
        Response availablePetsRes = petAPIHelper.getAllPetsByStatus("available");

        JSONArray allPetsArray = new JSONArray(availablePetsRes.asString());

        // Iterate over each pets and update pet name
        allPetsArray.forEach(petObject -> {
            JSONObject petJsonObject = (JSONObject) petObject;

            String newPetName = PetFaker.getPet();

            // Update value of pet name
            petJsonObject.put("name", newPetName);

            // Get Pet ID
            Object petID = petJsonObject.get("id");

            // Update Pet
            Response petUpdate = petAPIHelper.update(petJsonObject);

            logger.info("Pet ID [" + petID + "] Has New Name As : " + newPetName);

            Assert.assertEquals(new JSONObject(petUpdate.asString()).getString("name"), newPetName);
        });
    }

    @Test(priority = 3)
    public void updateAllPendingPets() {
        // Create object of API Helper
        PetAPIHelper petAPIHelper = new PetAPIHelper();

        // Get all available pets
        Response availablePetsRes = petAPIHelper.getAllPetsByStatus("pending");

        JSONArray allPetsArray = new JSONArray(availablePetsRes.asString());

        // Iterate over each pets and update pet name
        allPetsArray.forEach(petObject -> {
            JSONObject petJsonObject = (JSONObject) petObject;

            String newPetName = PetFaker.getPet();

            // Update value of pet name
            petJsonObject.put("name", newPetName);

            // Get Pet ID
            Object petID = petJsonObject.get("id");

            // Update Pet
            Response petUpdate = petAPIHelper.update(petJsonObject);

            logger.info("Pet ID [" + petID + "] Has New Name As : " + newPetName);

            Assert.assertEquals(new JSONObject(petUpdate.asString()).getString("name"), newPetName);
        });
    }

    @Test(priority = 4)
    public void updateAllSoldPets() {
        // Create object of API Helper
        PetAPIHelper petAPIHelper = new PetAPIHelper();

        // Get all available pets
        Response availablePetsRes = petAPIHelper.getAllPetsByStatus("sold");

        JSONArray allPetsArray = new JSONArray(availablePetsRes.asString());

        // Iterate over each pets and update pet name
        allPetsArray.forEach(petObject -> {
            JSONObject petJsonObject = (JSONObject) petObject;

            String newPetName = PetFaker.getPet();

            // Update value of pet name
            petJsonObject.put("name", newPetName);

            // Get Pet ID
            Object petID = petJsonObject.get("id");

            // Update Pet
            Response petUpdate = petAPIHelper.update(petJsonObject);

            logger.info("Pet ID [" + petID + "] Has New Name As : " + newPetName);

            Assert.assertEquals(new JSONObject(petUpdate.asString()).getString("name"), newPetName);
        });
    }
}
