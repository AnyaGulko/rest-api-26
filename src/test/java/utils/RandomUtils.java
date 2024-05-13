package utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class RandomUtils {
    static Faker faker = new Faker(Locale.ENGLISH);

    public static String getName (){
        return faker.name().firstName();
    }

    public static String getJob(){
        return faker.job().field();
    }
}
