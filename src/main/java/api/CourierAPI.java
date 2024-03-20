package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import requests.courier.CourierLoginReq;
import requests.courier.CreateCourierReq;

import static api.BaseAPI.baseReq;
import static api.BaseAPI.deleteReq;
import static configuration.files.Config.*;


public class CourierAPI {
    @Step("Отправка запроса на создание курьера")
    public static Response createCourierAPI(CreateCourierReq body){
        return baseReq(body, CREATE_COURIER_ENDPOINT);
    }

    @Step("Отправка запроса на вход под логином и паролем курьера")
    public static Response courierLoginAPI(CourierLoginReq body){
        return baseReq(body, COURIER_LOGIN_ENDPOINT);
    }
    @Step("Удаление курьера")
    public static void deleteCourierAPI(String id){
        deleteReq(DELETE_COURIER_ENDPOINT + id);
    }

}
