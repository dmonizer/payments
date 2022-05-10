package ee.sample.payments.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IBANTest {

    @ParameterizedTest
    @ArgumentsSource(ValidIBANProvider.class)
    void ibanValidation_valids(String iban) {
        assertDoesNotThrow(() -> new IBAN(iban));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidIBANProvider.class)
    void ibanValidation_invalids(String iban) {
        assertThrows(IllegalArgumentException.class, () -> new IBAN(iban));
    }


}
