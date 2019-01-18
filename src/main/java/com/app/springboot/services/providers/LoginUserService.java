package com.app.springboot.services.providers;

import com.app.springboot.clients.UserAPIClient;
import com.app.springboot.datatransfer.LoginData;
import com.app.springboot.datatransfer.LoginResultData;
import com.app.springboot.datatransfer.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService extends ProviderService {
    private static final String LOGIN_USERNAME = "kinexo";
    private static final String LOGIN_PASSWORD = "kinexo";

    @Autowired
    UserAPIClient userAPIClient;

    @Override
    public LoginResultData loginUser() throws Exception {

        LoginData loginData = new LoginData();
        loginData.setUsername(LOGIN_USERNAME);
        loginData.setPassword(LOGIN_PASSWORD);

        return userAPIClient.loginUserService(loginData);
    }

    @Override
    public UserData getUserData(Integer userId) throws Exception {
        LoginResultData loginResultData = loginUser();

        return userAPIClient.getUserData(loginResultData, userId);
    }
}
