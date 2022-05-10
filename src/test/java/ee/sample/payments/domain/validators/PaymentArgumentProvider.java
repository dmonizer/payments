package ee.sample.payments.domain.validators;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.payments.PaymentEntity;
import ee.sample.payments.domain.payments.PaymentType;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

public class PaymentArgumentProvider implements ArgumentsProvider {

    private final List<PaymentData> validPaymentData = List.of(
            new PaymentData("EUR", 1, PaymentType.TYPE_1, "has details", null),
            new PaymentData("USD", 1, PaymentType.TYPE_2, null, null),
            new PaymentData("EUR", 1, PaymentType.TYPE_3, null, "TESTBICASTHISISNOTVERIFIED"));

    private final List<PaymentData> invalidPaymentData = List.of(
            new PaymentData("EUR", 1, PaymentType.TYPE_1, null, null),
            new PaymentData("USD", -10, PaymentType.TYPE_2, null, null),
            new PaymentData("EUR", 1, PaymentType.TYPE_3, null, null));

    Stream<Arguments> getPaymentEntities(List<PaymentData> paymentData) {
        return paymentData
                .stream()
                .map(this::makePayment);
    }

    Arguments makePayment(PaymentData payment) {
        return Arguments.of(new PaymentEntity()
                .amount(new BigDecimal(payment.amount))
                .currency(Currency.getInstance(payment.currency))
                .type(payment.type)
                .bic(payment.bic)
                .details(payment.details)
                .debtorIban(new IBAN("EE3423423432"))
                .creditorIban(new IBAN("LV324342342")));
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        if (context.getRequiredTestMethod().getName().equals("isValid_happyPaths")) {
            return getPaymentEntities(validPaymentData);
        } else {
            if (context.getRequiredTestMethod().getName().equals("isValid_nonHappyPaths")) {
                return getPaymentEntities(invalidPaymentData);
            }
        }
        return Stream.empty();
    }

    private static class PaymentData {
        public String currency;
        public int amount;
        public PaymentType type;
        public String details;
        public String bic;

        public PaymentData(String currency, int amount, PaymentType type, String details, String bic) {
            this.currency = currency;
            this.amount = amount;
            this.type = type;
            this.details = details;
            this.bic = bic;
        }
    }
}

