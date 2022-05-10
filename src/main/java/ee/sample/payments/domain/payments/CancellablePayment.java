package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.fees.FeeEntity;
import ee.sample.payments.exceptions.PaymentCancellationError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface CancellablePayment {
    FeeEntity calculateCancellationFee();

    // here the default to be able to have potential different implementations,
    // idk, like type3 payment can only be cancelled if was done on thursday night on a crossroad
    // and signed with three drops of blood or smth :)
    default boolean isCancellationPossible(LocalDateTime paymentCreationTIme) {
        var todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        return !paymentCreationTIme.isBefore(todayMidnight);
    }
}
