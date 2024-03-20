package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import requests.order.CreateOrderReq;

import java.util.Map;

import static api.BaseAPI.baseReq;
import static api.BaseAPI.getReq;
import static configuration.files.Config.CREATE_ORDER_ENDPOINT;

public class OrderAPI {
    @Step("Отправка запроса на создание курьера")
    public static Response createOrderAPI(CreateOrderReq body){
        return baseReq(body, CREATE_ORDER_ENDPOINT);
    }

    @Step("Отправка запроса на получение даных по заказу")
    public static Response getOrderAPI(Map<String, Object> body){
        return getReq(body, CREATE_ORDER_ENDPOINT);
    }
}
