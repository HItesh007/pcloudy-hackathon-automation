package com.hackathon.pCloudy.tests.challangeOne;

import com.hackathon.pCloudy.pages.CPSatExamHomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class ChallengeOneTests {
    private static final Logger logger = LogManager.getLogger(ChallengeOneTests.class.getName());

    @Test(priority = 0)
    public void challengeOneProblemA() {
        try {

            CPSatExamHomePage homePage = new CPSatExamHomePage();
            String tamilToEng = homePage
                    .navigateAndConvertTo("Tamil");

            System.out.println("\n----------------- Following Is Translated From TAMIL -----------------\n");
            System.out.println(tamilToEng);

            String hindiToEn = homePage
                    .navigateAndConvertTo("Hindi");

            System.out.println("\n----------------- Following Is Translated From HINDI -----------------\n");
            System.out.println(hindiToEn);


            String kannadaToEn = homePage
                    .navigateAndConvertTo("Kannada");

            System.out.println("\n----------------- Following Is Translated From KANNADA -----------------\n");
            System.out.println(kannadaToEn);

            String frenchToEn = homePage
                    .navigateAndConvertTo("French");

            System.out.println("\n----------------- Following Is Translated From FRENCH -----------------\n");
            System.out.println(frenchToEn);


        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
