package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest extends BaseTest {

    //BDD syntax
    @Test
    public void checkApiUp1() {
        given()
                .spec(spec)
                .when()
                .get("/ping")
                .then()
                .assertThat()
                .statusCode(201);
    }

    //Classic java syntax
    @Test
    public void checkApiUp2() {
        Response response = RestAssured.given(spec).get("/ping");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201, but it's not");
    }
}