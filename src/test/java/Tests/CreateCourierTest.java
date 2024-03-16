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
import responses.courier.CreateCourierResponse;

import static api.CourierAPI.*;
import static configuration.files.ErrorMessages.COURIER_CREATE_GEMINI;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateCourierTest extends BaseTest {
    private CreateCourierReq createCourierReq;

    @Before
    public void createCourierForTest(){
        createCourierReq = new CreateCourierReq(
                "testlogin" + RandomStringUtils.randomAlphabetic(2),
                "MeGaPaSs123" + RandomStringUtils.randomNumeric(3),
                "NamelessCourier" +RandomStringUtils.randomNumeric(3)
        );
    }

    @Test
    @DisplayName("Метод проверки создания курьера")
    @Description("Проверка вызова метода и корретности статускода (ожидается 201)")
    public void checkCreateCourierResponse201Test(){
        Response response = CreateCourierAPI(createCourierReq);

        response.then()
                .assertThat()
                .statusCode(SC_CREATED);

        assertTrue("Ошибка при создании курьера",
                response.as(CreateCourierResponse.class).isOk());
    }

    @Test
    @DisplayName("Метод для проверки невозможности создания двух одинаковых курьеров")
    @Description("Метод проверяет, что невозможно создать два одинаковых курьера и корректность ответа метода")
    public void checkCreateCourierGeminiTest(){
        CreateCourierAPI(createCourierReq);

        Response response = CreateCourierAPI(createCourierReq);
        response.then()
                .assertThat()
                .statusCode(SC_CONFLICT); // status code 409

        assertEquals("Ожидается иной текст ошибки",
                COURIER_CREATE_GEMINI,
                response.as(CreateCourierResponse.class).getMessage());
    }

    @After
    @DisplayName("Метод удаления курьера (deleteCourierAfterCreateTest)")
    @Description("Удаляет созданного курьера после теста")
    public void deleteCourierAfterCreateTest(){
        Response response = CourierLoginAPI(
                new CourierLoginReq(
                createCourierReq.getLogin(),
                createCourierReq.getPassword()));
        DeleteCourierAPI(response.jsonPath().get("id").toString());

    }

}
