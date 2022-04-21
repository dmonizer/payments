package ee.sample.payments.services.implementations;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class PaymentsServiceImplTest {
    // TODO: these are end-to-end tests, which run on running instance and
    // they do not ensure cleanup after themselves.
    // Currently its not an issue, as its running on in-memory DB

    private static final String PAYMENT_JSON = "{\n" + "    \"type\": \"TYPE_2\",\n" + "    \"amount\": 50,\n" + "    \"debtorIban\": \"EE3434242343\",\n" + "    \"creditorIban\": \"EE131231231231\",\n" + "    \"currency\": \"USD\",\n" + "    \"details\": \"payment details\",\n" + "    \"cancelled\": false\n" + "}";

    @Test
    void createPayment() {
        with().contentType("application/json").body(PAYMENT_JSON).post("/create").then().assertThat().statusCode(200).body("id", notNullValue());

    }

    @Test
    void bydebtor() {
        with().contentType("application/json").get("/bydebtor/{iban}", "EE14121231") // checking for payment created in data.sql
                .then().statusCode(200);
    }

    @Test
    void bycreditor() {
        with().contentType("application/json").get("/bycreditor/{iban}", "LV3434234234") // checking for payment created in data.sql
                .then().statusCode(200);
    }

    @Test
    void cancelPayment() {
        Integer paymentId = with().contentType("application/json").body(PAYMENT_JSON).post("/create").then().extract().path("id");

        with().contentType("application/json").post("/cancel/{id}", paymentId).then().statusCode(200);

        with().contentType("application/json").get("/byid/{id}", paymentId).then().statusCode(200).body("cancelled", equalTo(true));


    }
    // TODO: fee check


}
