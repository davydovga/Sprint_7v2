package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.courier.CourierLoginReq;
import requests.courier.CreateCourierReq;
import responses.courier.CourierLoginResponse;

import static api.CourierAPI.*;
import static configuration.files.ErrorMessages.COURIER_LOGIN_NOT_FOUND;
import static org.junit.Assert.*;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierLoginTest extends BaseTest {
    private CourierLoginReq courierLoginReq;

    @Before
    public void createCourierForTest(){
        CreateCourierReq createCourierReq = new CreateCourierReq(
                "testlogin" + RandomStringUtils.randomAlphabetic(3),
                "MeGaPaSs123" + RandomStringUtils.randomNumeric(3),
                "NamelessCourier" + RandomStringUtils.randomNumeric(3)
        );

        courierLoginReq = new CourierLoginReq(
                createCourierReq.getLogin(),
                createCourierReq.getPassword());

        CreateCourierAPI(createCourierReq);
    }

    @Test
    @DisplayName("Метод проверки входа под созданной учетной записью курьера (checkCourierCanLoginTest)")
    @Description("Метод создает экземпляр курьера и проверяет возможность войти под его данными и проверяет ответ метода")
    public void checkCourierCanLoginTest(){
        Response response = CourierLoginAPI(courierLoginReq);

        response.then()
                .assertThat()
                .and()
                .statusCode(SC_OK); // status code 200
        assertNotEquals("Метод вернул 0",0,response.as(CourierLoginResponse.class).getId());
    }

    @Test
    @DisplayName("Метод проверки входа с несуществующей УЗ (checkCourierInvalidDataTest)")
    @Description("Метод проверяет корректность ответа метода при попытке войти под несуществующей УЗ")
    public void checkCourierInvalidDataTest(){
        CourierLoginReq courierLoginInvalidReq = new CourierLoginReq(
                courierLoginReq.getLogin() + RandomStringUtils.randomNumeric(3),
                courierLoginReq.getPassword() + RandomStringUtils.randomAlphabetic(3)
        );
        Response response = CourierLoginAPI(courierLoginInvalidReq);
        response.then()
                .assertThat()
                .statusCode(SC_NOT_FOUND); // status code 404

        assertEquals("Сообщение",COURIER_LOGIN_NOT_FOUND,response.as(CourierLoginResponse.class).getMessage());

    }

    @After
    @DisplayName("Метод удаления курьера (deleteCourierAfterTest)")
    @Description("Удаляет созданного курьера после теста")
    public void deleteCourierAfterTest(){
        Response response = CourierLoginAPI(courierLoginReq);
        DeleteCourierAPI(response.jsonPath().get("id").toString());
    }


}
