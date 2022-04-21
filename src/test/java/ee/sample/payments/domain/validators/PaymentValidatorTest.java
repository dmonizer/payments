package ee.sample.payments.domain.validators;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.payments.PaymentEntity;
import ee.sample.payments.domain.payments.PaymentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

class PaymentValidatorTest {

    @Test
    void isValid_happyPaths() {
        Assertions.assertTrue(new PaymentValidator(makePayment("EUR", 1, PaymentType.TYPE_1).details("details present")).isValid());
        Assertions.assertTrue(new PaymentValidator(makePayment("USD", 1, PaymentType.TYPE_2)).isValid());
        Assertions.assertTrue(new PaymentValidator(makePayment("EUR", 1, PaymentType.TYPE_3).bic("TESTBICASTHISISNOTVERIFIED")).isValid());

    }

    @Test
    void isValid_nonHappyPaths() {
        Assertions.assertFalse(new PaymentValidator(makePayment("EUR", 1, PaymentType.TYPE_1).details(null)).isValid());

        Assertions.assertFalse(new PaymentValidator(makePayment("USD", -10, PaymentType.TYPE_2)).isValid());

        Assertions.assertFalse(new PaymentValidator(makePayment("EUR", 1, PaymentType.TYPE_3).bic(null)).isValid());

    }

    PaymentEntity makePayment(String currency, double amount, PaymentType type) {
        return new PaymentEntity().amount(new BigDecimal(amount)).currency(Currency.getInstance(currency)).type(type).debtorIban(new IBAN("EE3423423432")).creditorIban(new IBAN("LV324342342"));

    }
}
