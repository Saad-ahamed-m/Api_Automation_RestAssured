package com.quinbay.restassured;
import com.quinbay.restassured.models.Example;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredDemo {
    public static void main(String args[])
    {
        RestAssured.baseURI="https://reqres.in/";
        given().queryParam("page",2).header("Content-Type","application/json").log().all().when().get("/api/users").then().statusCode(200).assertThat().body("page",equalTo(2)).log().all();
        String body="{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        given().header("Content-Type","application/json").log().all().when().body(body).post("/api/users").then().statusCode(201).assertThat().body("name",equalTo("morpheus")).assertThat().body("job",equalTo("leader")).assertThat().body(matchesJsonSchemaInClasspath("Test1.json")).log().all();
        Response response=given().queryParam("page",2).header("Content-Type","application/json").log().all().when().get("/api/users");

        String respondStr =response.asString();
        System.out.println(response);
        response.prettyPrint();
        assertThat("Status code is not 200",response.getStatusCode(),equalTo(200));
        ObjectMapper obj=new ObjectMapper();
        try{
            Example pojo= obj.readValue(respondStr ,Example.class);
            assertThat("Page is not matching",pojo.getPage(),equalTo(2));
            assertThat("Size is not matching",pojo.getData().size(),equalTo(6));
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }
  //        assertThat("Duration taken more than expected",response.getTime(),equalTo(500));
         assertThat("Duration is higher than expected",response.timeIn(TimeUnit.SECONDS),lessThan(10L));
    }
}
