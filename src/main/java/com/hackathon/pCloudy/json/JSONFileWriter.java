package com.hackathon.pCloudy.json;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONFileWriter {
    public JSONFileWriter() {
        // To do
    }

    /**
     * Save a string as a json file
     * @param targetName
     * @param jsonText
     * @throws IOException
     */
    public void saveJSONStringToFile(String targetName, String jsonText) throws IOException {
        Pattern xlsRegexPattern = Pattern.compile("[\\w\\s\\d`~!@$%^&*()-_=+{}|;:'\"?.>,<?]*.json$", Pattern.CASE_INSENSITIVE);
        Matcher xlsMatcher = xlsRegexPattern.matcher(targetName);
        Path path;
        try {
            if (xlsMatcher.matches()) {
                path = Paths.get(targetName);
            } else {
                path = Paths.get(targetName + ".json");
            }

            BufferedWriter writer = Files.newBufferedWriter(path);
            writer.write(jsonText);
            writer.close();
            System.out.println("Json file " + path.getFileName() +" written at Path : " + path.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
