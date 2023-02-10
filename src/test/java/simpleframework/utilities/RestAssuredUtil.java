package simpleframework.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestAssuredUtil {
    //sets base uri
    public static void setBaseURI(){
        RestAssured.baseURI= "http://generator.swagger.io/";
    }
    //set base path
    public static void setBasePath(String basePathterm){
        RestAssured.basePath = basePathterm;
    }
    //Reset base uri
    public static void resetBaseURI(){
        RestAssured.baseURI=null;
    }
    //reset base path
    public static void resetBasePath(){
        RestAssured.basePath=null;
    }
    //set contenttype
    public static void setContentType(ContentType type) {
        given().contentType(type);
    }
    //returns response by given path
    public static Response getResponse(String path){
        return given().get(path);
    }
    //return JsonObject object
    public static JsonPath getJSONPath(Response res){
        String json = res.asString();
        return new JsonPath(json);
    }
}
