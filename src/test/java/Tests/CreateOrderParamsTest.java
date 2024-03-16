package Tests;

import configuration.files.VehicleColour;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.order.CreateOrderReq;
import responses.order.CreateOrderResponse;

import static api.OrderAPI.CreateOrderAPI;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertNotEquals;


@RunWith(Parameterized.class)
@AllArgsConstructor
public class CreateOrderParamsTest extends BaseTest {

    private String[] colour;
    private final String comment;


    private final CreateOrderReq createOrderReq = new CreateOrderReq(
            "Sakura",
            "Haruno",
            "Konoha 3rd avenue",
            "Hokage's monument st.",
            "+7 800 355 35 35",
            120,
            "2024-06-19");


    @Parameterized.Parameters()
    public static Object[][] switchBetweenParamsCreateOrder() {
        return new Object[][]{
                {new String[]{
                        VehicleColour.Colours.BLACK.toString(),
                        VehicleColour.Colours.GREY.toString()
                }, "Техника призыва черно-серого самоката"},
                {new String[]{
                        VehicleColour.Colours.BLACK.toString()
                }, "Техника призыва черного самоката"},
                {new String[]{
                        VehicleColour.Colours.GREY.toString()
                }, "Техника призыва серого самоката"},
                {new String[]{
                }, "Техника призыва любого самоката"}
        };
    }



    @Before
    public void setParams(){
        createOrderReq.setColor(this.colour);
        createOrderReq.setComment(this.comment);
    }

    @Test
    @DisplayName("Метод проверяет корректность ответа метода при создании заказа (checkCreateOrderColorsSwitcherTest)")
    @Description("Метод получает статускод = 200 и проверяет что получен номер заказа не равный 0")
    public void checkCreateOrderColorsSwitcherTest(){
        Response response = CreateOrderAPI(createOrderReq);

        response.then().statusCode(SC_CREATED);
        assertNotEquals("Получено недопустимое значение track = 0",
                response.as(CreateOrderResponse.class).getTrack(),0);

    }
}
