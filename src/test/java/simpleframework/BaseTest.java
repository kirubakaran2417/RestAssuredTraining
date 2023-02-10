package simpleframework;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import simpleframework.utilities.RestAssuredUtil;
import simpleframework.utilities.TestUtil;

public class BaseTest {
    public Response res=null;//response
    public JsonPath jp=null;//json path

    //Instantiate a helper tester methods(testutils) object
    TestUtil tu=new TestUtil();

    @BeforeClass
    public void setup(){
        //test setup
        RestAssuredUtil.setBaseURI();//setup
        RestAssuredUtil.setBasePath("api");//setup base path
        RestAssuredUtil.setContentType(ContentType.JSON);
    }
    @AfterClass
    public void teardown(){
        //reset values
        RestAssuredUtil.resetBaseURI();
        RestAssuredUtil.resetBasePath();
    }
}
