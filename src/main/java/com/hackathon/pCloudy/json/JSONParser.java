package com.hackathon.pCloudy.json;

import java.io.FileReader;
import java.io.IOException;

public class JSONParser {

    public JSONParser() {
        // To Do
    }

    /**
     * Read JSON from A File Path As JSONObject
     * @param jsonFilePath String
     * @return JSONObject
     */
    public JSONObject parse(String jsonFilePath) {
        try{
            // Read Json File
            FileReader jsonFileReader = new FileReader(jsonFilePath);

            JSONTokener jsonTokener = new JSONTokener(jsonFileReader);

            return new JSONObject(jsonTokener);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Read JSON From a File As JSONObject
     * @param jsonFileReader FileReader
     * @return JSONObject
     */
    public JSONObject parse(FileReader jsonFileReader) {
        // Create JSON Tokener to Read JSON
        JSONTokener jsonTokener = new JSONTokener(jsonFileReader);

        return new JSONObject(jsonTokener);

    }

    /**
     * Read JSON from A File Path as JSON String
     * @param jsonFilePath String
     * @return JSONObject
     */
    public String parseAsString(String jsonFilePath) {
        try{
            // Read Json File
            FileReader jsonFileReader = new FileReader(jsonFilePath);

            JSONTokener jsonTokener = new JSONTokener(jsonFileReader);

            return (new JSONObject(jsonTokener)).toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Read JSON From a File As String
     * @param jsonFileReader FileReader
     * @return String
     */
    public String parseAsString(FileReader jsonFileReader) {
        // Create JSON Tokener to Read JSON
        JSONTokener jsonTokener = new JSONTokener(jsonFileReader);

        return (new JSONObject(jsonTokener)).toString();

    }


}
