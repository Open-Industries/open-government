package org.openindustries.government.functions.transparency;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"agencyId\":\"a-1\",\"measuredOverdueCases\":3,\"targetOverdueCases\":10,\"measuredOpenDataPct\":92.0,\"targetOpenDataPct\":90.0}")
                .when().post("/transparencyEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"agencyId\":\"a-2\",\"measuredOverdueCases\":48,\"targetOverdueCases\":10,\"measuredOpenDataPct\":92.0,\"targetOpenDataPct\":90.0}")
                .when().post("/transparencyEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
