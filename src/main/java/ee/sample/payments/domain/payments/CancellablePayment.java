package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.fees.FeeEntity;
import ee.sample.payments.exceptions.PaymentCancellationError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface CancellablePayment {
    FeeEntity calculateCancellationFee() throws PaymentCancellationError;

    default boolean isCancellationPossible(LocalDateTime paymentCreationTIme) { // here the default to be able to have potential different implementations, idk, type3 payment can only be cancelled if was done on thursday night on a crossroad :)
        var todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        return !paymentCreationTIme.isBefore(todayMidnight);
    }
}
