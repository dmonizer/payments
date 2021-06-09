package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.fees.FeeEntity;
import ee.sample.payments.exceptions.PaymentCancellationError;

import java.time.*;

public interface CancellablePayment {
  public FeeEntity calculateCancellationFee() throws PaymentCancellationError;

  default boolean isCancellationPossible(LocalDateTime paymentCreationTIme) {
    var todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    return !paymentCreationTIme.isBefore(todayMidnight);
  }
}
