package org.example;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssureddemo {

    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    @Test
    public void verifystatuscode(){
        RestAssured.baseURI="https://dummy.restapiexample.com/api/v1/employees";
        //create a request specification
        requestSpecification= given();
        //calling get method
        response=requestSpecification.get();
        //lets print the response body
        String resString=response.prettyPrint();
        System.out.println(resString);
        //To perform the validation on response,we need to get validatatble response
        validatableResponse=response.then();
        ResponseBody resBody=response.getBody();
        System.out.println(resBody.asString());
        //get status code
        validatableResponse.statusCode(200);
        //check the status line is as expected
        validatableResponse.statusLine("HTTP/1.1 200 OK");
    }

    @Test
    public void testcase2_BDD(){
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com")
                .when().get("/booking")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");

    }

    @Test
    public void NonBDDstylePOSTRequest(){
        String jsonstring = "{\"username\":\"admin\",\"password\":\"password123\"}";
        requestSpecification=given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/auth");
        requestSpecification.body(jsonstring);
        response=requestSpecification.post();
        System.out.println(response.asString());
        validatableResponse=response.then();
        validatableResponse.statusCode(200);
        validatableResponse.body("token",Matchers.notNullValue());
        validatableResponse.body("token.length()",Matchers.is(15));
        validatableResponse.body("token",Matchers.matchesRegex("^[a-zA-Z0-9]+$"));

    }

    @Test
    public void BDDstylePostRequest(){
        String jsonstring = "{\"username\":\"admin\",\"password\":\"password123\"}";
        RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/auth")
                .contentType(ContentType.JSON)
                .body(jsonstring)
                .when().post()
                .then().assertThat()
                .body("token",Matchers.notNullValue())
                .body("token.length()",Matchers.is(15))
                .body("token",Matchers.matchesRegex("^[a-zA-Z0-9]+$"));
    }
    @Test
    public void BDDstyleputRequest(){
        String jsonstring="{\"name\":\"kiruba\",\"job\":\"sdet consultant\"}";
        RestAssured.given().baseUri("https://reqres.in/api/users/2")
                .contentType(ContentType.JSON)
                .body(jsonstring)
                .when().put()
                .then().assertThat()
                .statusCode(200)
                .body("name",Matchers.equalTo("kiruba"));
    }
    @Test
    public void BDDstylepatchRequest(){
        String jsonstring="{\"name\":\"kiruba\",\"job\":\"devops consultant\"}";
        RestAssured.given().baseUri("https://reqres.in/api/users/2")
                .contentType(ContentType.JSON)
                .body(jsonstring)
                .when().patch()
                .then().assertThat()
                .statusCode(200)
                .body("job",Matchers.equalTo("devops consultant"));
    }
    @Test
    public void BDDstyleDeleteRequest(){
        RestAssured.given().baseUri("https://reqres.in/api/users/2")


                .when().delete()
                .then().assertThat()
                .statusCode(204);

    }

    /*
    gettime()-milliseconds
    gettimein(Timeunit unit)-
    time()
    timein()
     */
    @Test
    public void Responsetime(){
        String Jsonstring="{\"name\":\"deepthi\",\"job\":\"devops consultant\"}";
        requestSpecification=RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.baseUri("https://reqres.in/api/users/2");
        requestSpecification.body(Jsonstring);
        response=requestSpecification.patch();
        validatableResponse=response.then();
        long responseTime=response.getTime();
        System.out.println("Response time in ms using gettime: " + responseTime);
        validatableResponse.time(Matchers.lessThan(2000L));
        validatableResponse.time(Matchers.lessThan(2L), TimeUnit.SECONDS);
    }
    @Test
    public void testHeaders(){
        RestAssured.baseURI="https://reqres.in/api/users?page=2";
        requestSpecification=RestAssured.given();
        response=requestSpecification.get();
        Headers allheaders=response.headers();
        for (Header header : allheaders){
            System.out.println("Key: " + header.getName() +" "+ "Value: " + header.getValue());
        }
    }

    @Test
    public void UserRegistrationSuccessful(){
        RestAssured.baseURI="https://demoqa.com";
        requestSpecification=RestAssured.given();
        JSONObject requetparams=new JSONObject();
        requetparams.put("username","test_rest");
        requetparams.put("password","rest@123");
        requestSpecification.body(requetparams.toString());
        response=requestSpecification.post("/Account/v1/User");
        ResponseBody body =response.getBody();
        System.out.println(response.body().asString());
        if(response.statusCode() ==200){
            //deserialize the response bodyinto json response
            JsonFailureResponse responsebody=body.as(JsonFailureResponse.class);
            //use the JsonFailureResponse class instance to assert the values
            Assert.assertEquals("user already exists",responsebody.Faultid);
            Assert.assertEquals("FAULT_USER_ALREADY_EXISTS",responsebody.Fault);
        }
    }
}
