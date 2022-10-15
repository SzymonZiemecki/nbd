package pl.nbd.EntityFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.javamoney.moneta.Money;
import pl.nbd.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityFactory {
    public static Address getAddres() {
        return new Address(generateRandomString(10), generateRandomString(10), generateRandomString(10));
    }

    public static Client getClient() {
        return new Client(generateRandomString(10), generateRandomString(10), getAddres(), generateMoney());
    }

    public static Laptop getLaptop() {
        return new Laptop(getRandomNumber(10, 1000),
                generateRandomString(10),
                generateRandomString(10),
                generateRandomString(10), generateMoney(),
                generateRandomString(10),
                getRandomNumber(2, 64),
                getRandomNumber(2, 64));
    }

    private static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(getLaptop());
        items.add(getLaptop());
        return items;
    }

    public static Order getOrder() {
        return new Order(getClient(), getAddres(), getItems(), generateMoney(), false);
    }

    private static String generateRandomString(int length) {
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }

    private static Money generateMoney() {
        return Money.of(getRandomNumber(20, 10000), "PLN");
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
