package org.openindustries.government.eligibility;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class EligibilityRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"housing-standard\",\"programType\":\"HOUSING\",\"maxBenefitMinor\":500000,\"maxProcessingDays\":30,\"priority\":1}")
                .when().post("/api/v1/eligibility-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/eligibility-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"programType\":\"HOUSING\",\"requiredMaxProcessingDays\":45,\"requiredMaxBenefitMinor\":300000}")
                .when().post("/api/v1/eligibility-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"programType\":\"BUSINESS\",\"requiredMaxProcessingDays\":45,\"requiredMaxBenefitMinor\":300000}")
                .when().post("/api/v1/eligibility-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
