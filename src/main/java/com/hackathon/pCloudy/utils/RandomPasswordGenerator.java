package com.hackathon.pCloudy.utils;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class RandomPasswordGenerator {

    /**
     * To Generate Random Password based on the length provided in parameter
     *
     * @param passwordLength int <p> Password length to generate </p>
     * @return String <p> Returns a password based on different policy applied </p>
     */
    public static String generatePassword(int passwordLength) {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        //lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        //upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        //digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "INVALID_PASSWORD";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        //splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(passwordLength, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
    }
}
