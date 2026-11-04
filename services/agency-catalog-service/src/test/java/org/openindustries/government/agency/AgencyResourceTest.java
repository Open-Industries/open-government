package org.openindustries.government.agency;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AgencyResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Urban Development Agency\",\"level\":\"MUNICIPAL\",\"servicesCount\":24}")
                .when().post("/api/v1/agencies")
                .then().statusCode(201)
                .body("status", is("ACTIVE"))
                .body("level", is("MUNICIPAL"))
                .extract().path("id");

        given().when().post("/api/v1/agencies/" + id + "/suspend")
                .then().statusCode(200).body("status", is("SUSPENDED"));
    }
}
