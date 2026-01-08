package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateAndGetBooking extends BaseTest {
    @Test
    public void createNewBookingAndGetItBasedOnItsId() {

        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking
        int newBookingID = responseCreate.jsonPath().getInt("bookingid");

        //Set Path Parameter
        spec.pathParam("bookingId", newBookingID);

        //Get response for specific booking id
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Ceren", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Cakir", "lastname in response is not expected");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 125, "total price in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it is true");

        String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2026-01-07", "CheckIn in response is not as expected");

        String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2026-01-10", "CheckOut in response is not expected");

        softAssert.assertAll();  // if we didn't add this, test result can be false positive.
        // Otherwise, test will be passed.
    }
}
