package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.courier.CourierLoginReq;
import requests.courier.CreateCourierReq;
import responses.courier.CourierLoginResponse;

import static configuration.files.ErrorMessages.COURIER_LOGIN_BAD_REQ;
import static org.junit.Assert.*;

import static api.CourierAPI.CourierLoginAPI;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
public class CourierLoginWithoutParamsTest extends BaseTest {
    private final String login;
    private final String password;
    private final String firstName = RandomStringUtils.randomNumeric(5);

    public CourierLoginWithoutParamsTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters()
    public static Object[][] switchBetweenParamsCourierLogin() {
        return new Object[][]{
                //Тело запроса с пустым логином
                {null, "MeGaPaSs123" + RandomStringUtils.randomNumeric(3)},
                //Тело запроса с пустым паролем
                {"courierlogin" + RandomStringUtils.randomAlphabetic(2), ""}
        };
    }

    @Test
    @DisplayName("Метод для проверки невозможности авторизации курьера без одного из параметров (checkCourierLoginWithoutSomeParamsTest)")
    @Description("На вход передается два набора параметров с пустым логином и паролем и проверяет корректность ответа метода")
    public void checkCourierLoginWithoutSomeParamsTest() {
        CreateCourierReq parametrizedCourierCreateReq = new CreateCourierReq(login,password,firstName);
        CourierLoginReq parametrizedCourierLoginReq = new CourierLoginReq(
                parametrizedCourierCreateReq.getLogin(),
                parametrizedCourierCreateReq.getPassword());


        Response response = CourierLoginAPI(parametrizedCourierLoginReq);
        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST); // status code 400

        assertEquals(
                "Сообщение об ошибке отличается",
                COURIER_LOGIN_BAD_REQ,
                response.as(CourierLoginResponse.class).getMessage());

    }

}
