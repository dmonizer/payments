package ee.sample.payments.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IBANTest {

    @Test
    void ibanValidation() {
        assertThrows(IllegalArgumentException.class, () -> new IBAN("UG34394923843883223810"));
        assertDoesNotThrow(() -> new IBAN("EE34394923843883223810"));
        assertDoesNotThrow(() -> new IBAN("lv3435423423420"));
    }
}
