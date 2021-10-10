package com.hackerearth.controller;

import com.hackerearth.dao.Portfolio;
import com.hackerearth.dao.Token;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public static ResponseEntity getOrderSuccessResponse(String orderId){
        Map<String, String> response = new HashMap();
        response.put("orderId",orderId);
        JSONObject obj = new JSONObject(response);
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    public static ResponseEntity getFailureResponse(String message){
        Map<String, String> response = new HashMap();
        response.put("error", message);
        JSONObject obj = new JSONObject(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obj);
    }

    public static ResponseEntity getTokenSuccessResponse(Token token){
        Map<String, String> response = new HashMap();
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    public static ResponseEntity getPortfolioSuccessResonse(Portfolio portfolio){
        Map<String, String> response = new HashMap();
        return ResponseEntity.status(HttpStatus.OK).body(portfolio);
    }

}
