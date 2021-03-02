package com.quinbay.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class ResponseSpecificationDemo {
    public static void main(String args[])
    {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://reqres.in/");
        reqBuilder.setBasePath("/api");
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.log(LogDetail.ALL);
        RequestSpecification reqSpec = reqBuilder.build();
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L))
                .log(LogDetail.ALL)
                .build();
        given()
                .queryParam("page", "2")
                .spec(reqSpec)
                .when()
                .get("/users")
                .then()
                .spec(responseSpecification);

        given().queryParam("page","2").spec(reqSpec).when().get("/unknown").then().spec(responseSpecification);

    }
}
