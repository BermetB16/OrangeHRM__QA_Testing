package com.learnaqa.utils;

import com.github.javafaker.Faker;
import java.util.Locale;

public class RandomEmployeeGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomEmployeeId() {
        // Генерируем 5-значное число (от 10000 до 99999), чтобы не пересекаться с существующими 4-значными
        return String.valueOf(faker.number().numberBetween(10000, 99999));
    }
}
