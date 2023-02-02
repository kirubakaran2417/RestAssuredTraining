package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

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
}
