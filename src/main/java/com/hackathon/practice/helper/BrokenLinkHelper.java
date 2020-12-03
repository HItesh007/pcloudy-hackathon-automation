package com.hackathon.practice.helper;

import io.restassured.response.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static io.restassured.RestAssured.given;

public class BrokenLinkHelper {

    /**
     * To check whether a specific url is broken or not using RestAssured
     *
     * @param linkUrl Link URL To Test for it's operational
     * @return true if link is broken, false otherwise
     */
    public static boolean isLinkBroken(String linkUrl) {
        boolean isLinkBroken = true;

        try {
            Response apiResponse = given()
                    .when()
                    .get(new URI(linkUrl))
                    .then()
                    .extract()
                    .response();
            isLinkBroken = apiResponse.getStatusCode() >= 400;
        } catch (Exception ignored) {
        }

        return isLinkBroken;
    }

    /**
     * To check whether a specific {@link URL} is broken or not using {@link HttpURLConnection}
     * and Response Message
     *
     * @param url {@link URL} to check for it's operational status
     * @return Response message
     */
    public static String isBrokenLink(URL url) {
        String response;

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Create Connection
            connection.connect();

            // Get Response message
            response = connection.getResponseMessage();

            connection.disconnect();

            return response;

        } catch (Exception exp) {
            return exp.getMessage();
        }
    }

    /**
     * To check whether a specific url is broken or not using {@link HttpURLConnection}
     * and a status code
     *
     * @param Url URL To check for it's operational status
     * @return true if URL is broken, false otherwise
     */
    public static boolean isBrokenLink(String Url) {
        boolean isLinkBroken = true;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(Url).openConnection());

            // We can set Request type as "HEAD" instead of "GET".
            // So that only headers are returned and not document body.
            httpURLConnection.setRequestMethod("HEAD");

            // On invoking connect() method,
            // actual connection to url is established and the request is sent.
            httpURLConnection.connect();

            int respCode = httpURLConnection.getResponseCode();

            isLinkBroken = respCode >= 400;
        } catch (Exception ignored) {
        }

        return isLinkBroken;
    }

    /**
     * TO check whether URL Is Working Or Not using {@link HttpURLConnection} & {@link CloseableHttpClient}
     *
     * @param URL To Check for it's working condition
     * @return true if it's working, false otherwise
     */
    public static boolean isURLWorking(String URL) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(URL);

            CloseableHttpResponse response = client.execute(request);
            // verifying response code and The HttpStatus should be 200 if not,
            // increment invalid link count
            ////We can also check for 404 status code like response.getStatusLine().getStatusCode() == 404
            return response.getStatusLine().getStatusCode() == 200;
        } catch (Exception ignored) {
        }
        return false;
    }
}