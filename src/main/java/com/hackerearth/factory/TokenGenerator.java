package com.hackerearth.factory;

import com.hackerearth.dao.Token;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TokenGenerator {
    public Token getToken(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();

        String generatedToken = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        //check if token already present. if yes call getToken once again.
        Token token = new Token();
        token.setTokenId(generatedToken);
        return token;
    }
}
