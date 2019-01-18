package com.app.springboot.services.providers;

import com.app.springboot.datatransfer.LoginResultData;
import com.app.springboot.datatransfer.UserData;

public abstract class ProviderService {
    public abstract LoginResultData loginUser() throws Exception;
    public abstract UserData getUserData(Integer userId) throws Exception;
}
