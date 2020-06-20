package com.hackathon.pCloudy.tests.challengeZero;

import com.hackathon.pCloudy.base.SeleniumBase;
import com.hackathon.pCloudy.enums.Browser;
import com.hackathon.pCloudy.helpers.JFairy.JFairyHelper;
import com.hackathon.pCloudy.json.JSONArray;
import com.hackathon.pCloudy.json.JSONObject;
import com.hackathon.pCloudy.thread.ThreadLocalSEDriver;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiChallengeTests {
    private static final Logger logger = LogManager.getLogger(ApiChallengeTests.class.getName());
    private static final String API_BASE_URI = "http://35.188.114.237:8088/api/v2";

    @Test(priority = 0)
    public void challengeZeroProblemA() {
        try {
            RestAssured.baseURI = API_BASE_URI;

            Response apiResponse = given()
                    .when()
                    .get("/users")
                    .then()
                    .extract()
                    .response();
            JSONObject jsonObject = new JSONObject(apiResponse.asString());
            JSONArray jsonArray = jsonObject.getJSONArray("user");

            jsonArray.forEach(jsonObj -> {
                System.out.println(((JSONObject) jsonObj).getInt("id"));
            });

            Assert.assertEquals(apiResponse.getStatusCode(), 200);
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }

    @Test(priority = 1)
    public void challengeZeroProblemB() {
        try {

            RestAssured.baseURI = API_BASE_URI;

            RequestSpecification requestSpecification = RestAssured.given();
            requestSpecification.header("Content-Type", "application/json");

            //region Add User

            String firstName = JFairyHelper.getFirstName(JFairyHelper.Gender.Male);
            String lastName = JFairyHelper.getLastName(JFairyHelper.Gender.Male);
            String address = "CThackATAhon";

            JSONObject requestObject = new JSONObject();
            requestObject.put("name", firstName);
            requestObject.put("surname", lastName);
            requestObject.put("address", address);

            // set Request Body
            requestSpecification.body(requestObject.toString());

            Response response = requestSpecification.post("/addusers");

            // Assert the response code for Add user API Call
            Assert.assertEquals(response.getStatusCode(), 200);

            // Capture response as json
            JSONObject responseObject = new JSONObject(response.asString());

            // Get ID
            int primaryKey = responseObject.getInt("id");

            //endregion

            //region Update User
            // Generate new user names
            firstName = JFairyHelper.getFirstName(JFairyHelper.Gender.Female);
            lastName = JFairyHelper.getLastName(JFairyHelper.Gender.Female);
            address = "CThackATAhon - Updated";

            // Create new request Object
            JSONObject updateRequestObj = new JSONObject();
            updateRequestObj.put("id", primaryKey);
            updateRequestObj.put("name", firstName);
            updateRequestObj.put("surname", lastName);
            updateRequestObj.put("address", address);

            // Set update Object
            requestSpecification.body(updateRequestObj.toString());

            // Hit PUT API
            Response updateResponse = requestSpecification.put("/updateuser");

            // Assert the response code for Add user API Call
            Assert.assertEquals(updateResponse.getStatusCode(), 200);

            //endregion

            //region Delete User
            // Delete user
            Response deleteResponse = requestSpecification.delete("/" + primaryKey + "/deleteuser");

            // Assert the response code for Add user API Call
            Assert.assertEquals(deleteResponse.getStatusCode(), 200);
            //endregion

            //region Negative Delete Test Case
            // Delete again
            Response negativeDeleteResponse = requestSpecification.delete("/" + primaryKey + "/deleteuser");

            // Assert that it's not available in dB
            Assert.assertEquals(negativeDeleteResponse.getStatusCode(), 404);
            //endregion

        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }

    @Test(priority = 2)
    public void challengeZeroProblemC() {
        try {
            RestAssured.baseURI = API_BASE_URI;

            RequestSpecification requestSpecification = RestAssured.given();
            requestSpecification.header("Content-Type", "application/json");

            //region Add User

            String firstName = JFairyHelper.getFirstName(JFairyHelper.Gender.Male);
            String lastName = JFairyHelper.getLastName(JFairyHelper.Gender.Male);
            String address = "CThackATAhon";

            JSONObject requestObject = new JSONObject();
            requestObject.put("name", firstName);
            requestObject.put("surname", lastName);
            requestObject.put("address", address);

            // set Request Body
            requestSpecification.body(requestObject.toString());

            Response response = requestSpecification.post("/addusers");

            // Assert the response code for Add user API Call
            Assert.assertEquals(response.getStatusCode(), 200);

            // Capture response as json
            JSONObject responseObject = new JSONObject(response.asString());

            // Get ID
            int primaryKey = responseObject.getInt("id");
            //endregion

            // Switch to Web And Get ID
            SeleniumBase seleniumBase = new SeleniumBase();
            seleniumBase.initBrowser(Browser.Chrome);

            // Open Url
            ThreadLocalSEDriver.getDriver().get("https://cpsatexam.org/index.php/challenge-0/");

            // Get the Ids & Verify it
            List<WebElement> idElements = ThreadLocalSEDriver
                    .getDriver()
                    .findElement(By.tagName("table"))
                    .findElements(By.tagName("td"));

            // Verify the id
            for (WebElement ele: idElements) {
                if(ele.getText().equalsIgnoreCase(String.valueOf(primaryKey))) {
                    System.out.println("Record with ID : " + primaryKey + " has been found on Web");
                    break;
                }
            }


            //region Delete the user
            // Delete user
            Response deleteResponse = requestSpecification.delete("/" + primaryKey + "/deleteuser");

            // Assert the response code for Add user API Call
            Assert.assertEquals(deleteResponse.getStatusCode(), 200);
            //endregion

            // Refresh the page and verify again
            ThreadLocalSEDriver.getDriver().navigate().refresh();

            // Get the Ids & Verify it
            List<WebElement> idElementsAfterDelete = ThreadLocalSEDriver
                    .getDriver()
                    .findElement(By.tagName("table"))
                    .findElements(By.tagName("td"));

            boolean isFound = false;
            // Verify the id
            for (WebElement ele: idElementsAfterDelete) {
                if(ele.getText().equalsIgnoreCase(String.valueOf(primaryKey))) {
                    isFound = true;
                    break;
                }
            }

            Assert.assertEquals(isFound, false);

            // Close the browser
            seleniumBase.closeBrowser();

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
