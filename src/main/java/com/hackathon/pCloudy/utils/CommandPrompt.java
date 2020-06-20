package com.hackathon.pCloudy.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandPrompt {

    Process p;
    ProcessBuilder builder;
    private static final Logger logger = LogManager.getLogger(CommandPrompt.class.getName());

    /**
     * This method run command on windows and mac
     * @param command to run
     */
    public String runCommand(String command) {
        String allLine = "";

        try {
            String os = System.getProperty("os.name");
            //System.out.println(os);

            // build cmd process according to os
            if(os.contains("Windows")) // if windows
            {
                builder = new ProcessBuilder("cmd.exe","/c", command);
                builder.redirectErrorStream(true);
                Thread.sleep(1000);
                p = builder.start();
            }
            else // If Mac
                p = Runtime.getRuntime().exec(command);

            // get std output
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line="";

            int i=1;
            while((line=r.readLine()) != null){
                allLine=allLine+""+line+"\n";
                if(line.contains("Console LogLevel: debug"))
                    break;
                i++;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return allLine;
    }
}
