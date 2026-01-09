package com.herokuapp.restfulbooker;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingWithPOJOTest extends BaseTest{
    @Test
    public void createBookingTest(){
        // Please first, put Jackson (Databind) in the classpath to serialize objects
        // We are setting up request body by using POJO classes instead of JSONObject
        BookingDates bookingDates= new BookingDates("2026-01-07","2026-01-10");
        Booking booking= new Booking("Ceren","Cakir",125,false, bookingDates,"Baby crib");

        //Get Response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
        response.print();

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(),200,"Status code should be 200, but it is not");

        //Verification for fields
        SoftAssert softAssert= new SoftAssert();
        String actualFirstName= response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName,"Ceren","firstname in response is not as expected");
        String actualLastName= response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName,"Cakir","lastname in response is not as expected");
        int price= response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price,125,"price is not as expected");
        boolean depositpaid= response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it is true");
        String actualCheckIn= response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2026-01-07","CheckIn in response is not as expected");
        String actualCheckOut= response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2026-01-10", "CheckOut in response is not as expected");
        String actualAdditionalNeeds= response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds,"Baby crib", "Additional need is different");

        softAssert.assertAll();
    }
}
