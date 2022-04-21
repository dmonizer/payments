package ee.sample.payments.services;

import ee.sample.payments.domain.fees.FeeEntity;
import ee.sample.payments.domain.payments.CancellablePayment;
import ee.sample.payments.domain.payments.PaymentEntity;
import ee.sample.payments.exceptions.PaymentCancellationError;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static ee.sample.payments.domain.Defaults.DEFAULT_CURRENCY;
import static ee.sample.payments.domain.PaymentCancellationFeeCoefficients.getCancellationFeeCoefficient;


public class Payment implements CancellablePayment {
    @Getter
    private final PaymentEntity paymentEntity;

    public Payment(PaymentEntity payment) {
        this.paymentEntity = payment;
    }

    @Override
    public FeeEntity calculateCancellationFee() throws PaymentCancellationError {
        if (!isCancellationPossible(this.getPaymentEntity().getCreatedDateTime())) {
            throw new PaymentCancellationError("Payment can be cancelled only until midnight of the same day");
        }

        return new FeeEntity().payment(this.getPaymentEntity()).currency(DEFAULT_CURRENCY).amount(BigDecimal.valueOf(getPaymentAgeInHours() * getCancellationFeeCoefficient(this.getPaymentEntity().type())));

    }

    private long getPaymentAgeInHours() {
        return Duration.between(this.getPaymentEntity().getCreatedDateTime(), LocalDateTime.now()).toHours();
    }

}
