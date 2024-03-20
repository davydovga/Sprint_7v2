package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseAPI {
    public static Response baseReq(Object body,String endpoint){
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response deleteReq(String endpoint){
        return given()
                .when()
                .delete(endpoint);
    }

    public static Response getReq(Map<String, Object> map, String endpoint){
        return given()
                .queryParams(map)
                .when()
                .get(endpoint);
    }
}
