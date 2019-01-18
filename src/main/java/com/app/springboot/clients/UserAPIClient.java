package com.app.springboot.clients;

import com.app.springboot.datatransfer.LoginData;
import com.app.springboot.datatransfer.LoginResultData;
import com.app.springboot.datatransfer.UserData;
import com.app.springboot.exceptions.ApiException;
import com.app.springboot.exceptions.NotFoundException;
import com.app.springboot.exceptions.UnirestException;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserAPIClient {
    private static final String SERVICE_URL = "https://gentle-eyrie-95237.herokuapp.com/";

    @Autowired
    private Gson jsonParser;

    public LoginResultData loginUserService(LoginData loginData) throws Exception {
        try {
            LoginResultData loginResultData;
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");

            String requestBody = jsonParser.toJson(loginData);

            HttpResponse<String> loginResult = Unirest.post(SERVICE_URL + "login")
                    .headers(headers)
                    .body(requestBody)
                    .asString();

            if (loginResult.getStatus() == 200) {
                loginResultData = jsonParser.fromJson(loginResult.getBody(), LoginResultData.class);
            } else {
                throw new ApiException("Error en el servicio de login");
            }

            return loginResultData;
        } catch (com.mashape.unirest.http.exceptions.UnirestException e) {
            throw new UnirestException("Error al comunicarse con el servicio de login.");
        }
    }

    public UserData getUserData(LoginResultData loginResultData, Integer userId) throws Exception{
        UserData userData;
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", loginResultData.getToken());

        HttpResponse<String> userDataResult = Unirest.get(SERVICE_URL + "users/"+userId)
                .headers(headers)
                .asString();

        if (userDataResult.getStatus() == 200) {
            userData = jsonParser.fromJson(userDataResult.getBody(), UserData.class);
        } else {
            if (userDataResult.getStatus() == 404) {
                throw new NotFoundException("El usuario no existe");
            }
            else {
                throw new ApiException("Error al obtener los datos del usuario");
            }
        }

        return userData;
    }
}
