package org.openindustries.government.citizencase;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class CitizenCaseResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"caseType\":\"PERMIT\",\"referenceCode\":\"FOL-2026-99887\",\"agencyCode\":\"AG-URBAN\"}")
                .when().post("/api/v1/cases")
                .then().statusCode(201)
                .body("status", is("OPEN"))
                .body("referenceCodeMasked", endsWith("9887"))
                .extract().path("id");

        given().when().get("/api/v1/cases/" + id)
                .then().statusCode(200).body("status", is("OPEN"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"caseType\":\"PERMIT\",\"referenceCode\":\"FOL-2026-99887\",\"agencyCode\":\"AG-URBAN\"}")
                .when().post("/api/v1/cases")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/cases/" + id + "/assign")
                .then().statusCode(200).body("status", is("IN_PROGRESS"));
    }
}
