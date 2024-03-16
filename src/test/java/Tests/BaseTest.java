package Tests;

import configuration.files.Config;
import io.restassured.RestAssured;
import org.junit.Before;


public abstract class BaseTest {
    @Before
    public void setUP(){
        RestAssured.baseURI = Config.BASE_URI;
    }

}
