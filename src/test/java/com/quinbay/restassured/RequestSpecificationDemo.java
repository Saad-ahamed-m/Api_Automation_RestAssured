package com.quinbay.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
public class RequestSpecificationDemo {
   public static void main(String args[])
   {
       RequestSpecBuilder reqBuilder=new RequestSpecBuilder();
       reqBuilder.setBaseUri("https://reqres.in/");
       reqBuilder.setBasePath("/api");
       reqBuilder.setContentType(ContentType.JSON);
       reqBuilder.log(LogDetail.ALL);
       RequestSpecification reqSpec=reqBuilder.build();
       Response response = given()
               .queryParam("page", "2")
               .spec(reqSpec)
               .when().get("/users");
       response.prettyPrint();
   }
}
