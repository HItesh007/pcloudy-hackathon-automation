package com.hackathon.pCloudy.helpers.JFairy;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.person.PersonProperties;

public class JFairyHelper {
    public JFairyHelper() {
        // To Do
    }

    public static String getFullName(Gender gender) {
        Fairy jFairy = Fairy.create();

        Person person = null;

        if(gender == Gender.Male) {
            person = jFairy.person(PersonProperties.male());
        } else if (gender == Gender.Female) {
            person = jFairy.person(PersonProperties.female());

        }
        return person.getFullName().trim();
    }

    public static String getFirstName(Gender gender) {
        Fairy jFairy = Fairy.create();

        Person person = null;

        if(gender == Gender.Male) {
            person = jFairy.person(PersonProperties.male());
        } else if (gender == Gender.Female) {
            person = jFairy.person(PersonProperties.female());

        }
        return person.getFirstName().trim();
    }

    public static String getLastName(Gender gender) {
        Fairy jFairy = Fairy.create();

        Person person = null;

        if(gender == Gender.Male) {
            person = jFairy.person(PersonProperties.male());
        } else if (gender == Gender.Female) {
            person = jFairy.person(PersonProperties.female());

        }
        return person.getLastName().trim();
    }

    public static String getAddress() {
        Fairy jFairy = Fairy.create();
        return jFairy.person().getAddress().toString();
    }

    public enum Gender {
        Male,
        Female
    }
}
