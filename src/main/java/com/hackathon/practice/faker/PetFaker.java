package com.hackathon.practice.faker;

import com.github.javafaker.Faker;
import org.apache.commons.lang.WordUtils;

import java.util.Random;

public class PetFaker {

    public static String getPet() {
        return WordUtils
                .capitalizeFully(
                        new Faker(new Random())
                                .animal()
                                .name()
                );
    }

    public static String petUrl() {
        return new Faker(new Random())
                .internet()
                .image();
    }
}
