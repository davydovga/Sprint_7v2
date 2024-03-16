package Tests;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import responses.order.GetOrderResponse;

import java.util.List;
import java.util.Map;

import static api.OrderAPI.GetOrderAPI;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class GetOrderParamsTest extends BaseTest {

    Map<String, Object> body;

    @Parameterized.Parameters()
    public static List<Map<Object, Object>> getDataForOrder() {
        return List.of(
                Map.of(
                        "courierId","12345"),
                Map.of(
                        "nearestStation","2"),
                Map.of(
                        "limit","3"),
                Map.of(
                        "page","1"),
                Map.of()
        );
    }
    @Test
    @DisplayName("Метод проверки данных по заказу")
    @Description("Метод проверяет корректность ответа метода при получении информации по заказу (checkCreateOrderColorsSwitcherTest)")
    public void checkGetOrdersListStatusCode(){
        Response response = GetOrderAPI(body);

        response.then()
                .assertThat()
                .statusCode(SC_OK);

        assertNotNull("Не удалось получить информацию по заказу",
                response.as(GetOrderResponse.class)
                        .getOrders());
    }

}
