package com.hackerearth.factory;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OrderIdGenerator {
    public String getOrderId(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedOrderId = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedOrderId;
    }
}
