package ee.sample.payments.services.implementations;

import ee.sample.payments.TestCategories;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag(TestCategories.INTEGRATION_TEST)
class PaymentsServiceImplTest {

    @LocalServerPort
    int port;
    @Autowired
    @Qualifier("paymentJson")
    private String PAYMENT_JSON;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createPayment() {
        with()
                .contentType("application/json")
                .body(PAYMENT_JSON)
                .post("/create")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    void bydebtor() {
        with()
                .contentType("application/json")
                .get("/bydebtor/{iban}", "EE14121231") // checking for existing payment in DB
                .then()
                .statusCode(200);
    }

    @Test
    void bycreditor() {
        with()
                .contentType("application/json")
                .get("/bycreditor/{iban}", "LV3434234234") //  checking for existing payment in DB
                .then()
                .statusCode(200);
    }

    @Test
    void cancelPayment() {
        Integer paymentId = with()
                .contentType("application/json")
                .body(PAYMENT_JSON)
                .post("/create")
                .then()
                .extract()
                .path("id");

        with()
                .contentType("application/json")
                .post("/cancel/{id}", paymentId)
                .then()
                .statusCode(200);

        with()
                .contentType("application/json")
                .get("/byid/{id}", paymentId)
                .then()
                .statusCode(200)
                .body("cancelled", equalTo(true));
    }
    // TODO: fee check


}
