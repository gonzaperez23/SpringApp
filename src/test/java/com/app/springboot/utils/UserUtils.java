package com.app.springboot.utils;

import com.app.springboot.datatransfer.LoginResultData;
import com.app.springboot.datatransfer.UserData;

import java.util.Date;

public class UserUtils {
    public static UserData getUserData() {
        UserData userData = new UserData();

        userData.setId(1);
        userData.setCity(CityUtils.getCityData());
        userData.setBirthDate(new Date(10000000));
        userData.setFirstName("Nombre");
        userData.setLastName("Apellido");

        return userData;
    }

    public static LoginResultData getLoginResultData() {
        LoginResultData loginResultData = new LoginResultData();

        loginResultData.setToken("token");

        return loginResultData;
    }
}
