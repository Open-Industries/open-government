package org.openindustries.government.functions.caserisk;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"caseId\":\"c-1\",\"delayIndex\":6,\"documentGapIndex\":4,\"complaintIndex\":2}")
                .when().post("/caseRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"caseId\":\"c-2\",\"delayIndex\":90,\"documentGapIndex\":77,\"complaintIndex\":61}")
                .when().post("/caseRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
