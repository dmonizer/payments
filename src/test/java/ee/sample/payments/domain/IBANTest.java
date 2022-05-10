package ee.sample.payments.domain;

import ee.sample.payments.TestCategories;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Tag(TestCategories.UNIT_TEST)
class IBANTest {

    @ParameterizedTest
    @ArgumentsSource(ValidIBANProvider.class)
    void ibanValidation_happyPaths(String iban) {
        assertDoesNotThrow(() -> new IBAN(iban));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidIBANProvider.class)
    void ibanValidation_nonHappyPaths(String iban) {
        assertThrows(IllegalArgumentException.class, () -> new IBAN(iban));
    }


}
