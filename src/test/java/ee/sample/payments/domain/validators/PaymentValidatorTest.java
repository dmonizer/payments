package ee.sample.payments.domain.validators;

import ee.sample.payments.TestCategories;
import ee.sample.payments.domain.payments.PaymentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Tag(TestCategories.UNIT_TEST)
class PaymentValidatorTest {

    @ParameterizedTest
    @ArgumentsSource(PaymentArgumentProvider.class)
    void isValid_happyPaths(PaymentEntity payment) {
        Assertions.assertTrue(new PaymentValidator(payment).isValid());
    }

    @ParameterizedTest
    @ArgumentsSource(PaymentArgumentProvider.class)
    void isValid_nonHappyPaths(PaymentEntity payment) {
        Assertions.assertFalse(new PaymentValidator(payment).isValid());
    }
}
