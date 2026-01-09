package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

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

        //Deserialize response to BookingId class
        BookingId bookingId= response.as(BookingId.class);

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(),200,"Status code should be 200, but it is not");

        System.out.println("Request Body: \n" + booking.toString());
        System.out.println("Response Body: \n" + bookingId.getBooking().toString());
        //Verification for fields
        Assert.assertEquals(bookingId.getBooking().toString(), booking.toString(),"Booking data is not matching");
    }
}
