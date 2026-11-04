package org.openindustries.government.fees;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FeesResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"caseId\":\"11111111-1111-1111-1111-111111111111\",\"assessedFeeMinor\":25000}")
                .when().post("/api/v1/fees")
                .then().statusCode(201).body("assessedFeeMinor", is(25000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"ONLINE\",\"amountMinor\":10000}")
                .when().post("/api/v1/fees/" + id + "/payments")
                .then().statusCode(200)
                .body("remainingAfter", is(15000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"caseId\":\"11111111-1111-1111-1111-111111111111\",\"assessedFeeMinor\":25000}")
                .when().post("/api/v1/fees")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"ONLINE\",\"amountMinor\":90000}")
                .when().post("/api/v1/fees/" + id + "/payments")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/fees/" + id)
                .then().statusCode(200).body("assessedFeeMinor", is(25000));
    }
}
