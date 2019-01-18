package com.app.springboot.services.providers;

import com.app.springboot.clients.UserAPIClient;
import com.app.springboot.datatransfer.LoginData;
import com.app.springboot.datatransfer.LoginResultData;
import com.app.springboot.datatransfer.TaskListResponse;
import com.app.springboot.datatransfer.UserData;
import com.app.springboot.exceptions.ApiException;
import com.app.springboot.exceptions.NotFoundException;
import com.app.springboot.services.TaskService;
import com.app.springboot.utils.TaskUtils;
import com.app.springboot.utils.UserUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

public class LoginUserServiceTest {
    LoginUserService service;

    @Mock
    UserAPIClient mockUserAPIClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new LoginUserService();
        service.userAPIClient = mockUserAPIClient;
    }

    @Test
    public void loginUserSuccefully() throws Exception {
        Mockito.when(mockUserAPIClient.loginUserService(any(LoginData.class)))
                .thenReturn(UserUtils.getLoginResultData());

        LoginResultData loginResultData = service.loginUser();

        assertEquals("Validacion de token obtenido", "token", loginResultData.getToken());
    }

    @Test
    public void loginUserNotSuccefullyUnirestException() throws Exception {
        Mockito.when(mockUserAPIClient.loginUserService(any(LoginData.class)))
                .thenThrow(new UnirestException("Error al comunicarse con el servicio de login."));

        try {
            LoginResultData loginResultData = service.loginUser();
        } catch (UnirestException ex) {
            assertEquals("Error message", "Error al comunicarse con el servicio de login.", ex.getMessage());
        }
    }

    @Test
    public void loginUserNotSuccefullyApiException() throws Exception {
        Mockito.when(mockUserAPIClient.loginUserService(any(LoginData.class)))
                .thenThrow(new ApiException("Error en el servicio de login"));

        try {
            LoginResultData loginResultData = service.loginUser();
        } catch (ApiException ex) {
            assertEquals("Error message", "Error en el servicio de login", ex.getResult().getMessage());
        }
    }

    @Test
    public void getUserDataSuccesfully() throws Exception {
        Mockito.when(mockUserAPIClient.getUserData(any(LoginResultData.class), anyInt()))
                .thenReturn(UserUtils.getUserData());

        UserData userData = service.getUserData(1);

        assertEquals("Validacion de nombre", "Nombre", userData.getFirstName());
        assertEquals("Validacion de apellido", "Apellido", userData.getLastName());
    }

    @Test
    public void getUserDataNotSuccesfullyApiException() throws Exception {
        Mockito.when(mockUserAPIClient.getUserData(any(LoginResultData.class), anyInt()))
                .thenThrow(new ApiException("Error en el servicio de login"));

        try {
            UserData userData = service.getUserData(1);
        } catch (ApiException ex) {
            assertEquals("Error message", "Error en el servicio de login", ex.getResult().getMessage());
        }
    }

    @Test
    public void getUserDataNotSuccesfullyNotFoundException() throws Exception {
        Mockito.when(mockUserAPIClient.getUserData(any(LoginResultData.class), anyInt()))
                .thenThrow(new NotFoundException("Error en el servicio de login"));

        try {
            UserData userData = service.getUserData(1);
        } catch (NotFoundException ex) {
            assertEquals("Error message", "El usuario no existe", ex.getResult().getMessage());
            assertEquals("Error message", 404, ex.getResult().getStatus());
        }
    }
}
