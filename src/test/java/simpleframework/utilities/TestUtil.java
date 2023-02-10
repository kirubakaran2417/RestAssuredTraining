package simpleframework.utilities;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class TestUtil {
    //verify the http response status
    public void checkStatusIs200(Response res){
        Assert.assertEquals(res.getStatusCode(), 200);
    }
    //get clients

    public void checkheaders(){
        given().queryParam("gen","clients").when().get().then()
                .log().all().assertThat()
                .header("Content-Type", "application/json");
    }
    public <T> ArrayList<T> getClients(JsonPath jp){
        return jp.get();
    }
}
