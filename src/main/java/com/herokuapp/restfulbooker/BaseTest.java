package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").
                build();
    }

    protected Response createBooking() {
        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Ceren");
        body.put("lastname", "Cakir");
        body.put("totalprice", "125");
        body.put("depositpaid", false);
        body.put("additionalneeds", "Baby crib");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2026-01-07");
        bookingdates.put("checkout", "2026-01-10");
        body.put("bookingdates", bookingdates);

        //Get Response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString()).post("/booking");
        return response;
    }
}
