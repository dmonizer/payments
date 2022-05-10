package ee.sample.payments.domain;

import ee.sample.payments.TestCategories;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Tag(TestCategories.UNIT_TEST)
class IBANTest {

    @ParameterizedTest
    @ValueSource(strings = {"EE34394923843883223810","lv3435423423420"})
    void ibanValidation_happyPaths(String iban) {
        assertDoesNotThrow(() -> new IBAN(iban));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UG34394923843883223810"})
    void ibanValidation_nonHappyPaths(String iban) {
        assertThrows(IllegalArgumentException.class, () -> new IBAN(iban));
    }
}
