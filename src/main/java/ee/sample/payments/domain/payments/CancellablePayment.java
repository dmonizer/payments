package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.fees.FeeEntity;
import ee.sample.payments.exceptions.PaymentCancellationError;

import java.time.*;

public interface CancellablePayment {
  public FeeEntity calculateCancellationFee() throws PaymentCancellationError;

  default boolean isCancellationPossible(LocalDateTime paymentCreationTIme) {
    LocalDateTime todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1L);
    return paymentCreationTIme.isBefore(todayMidnight);
  }
}
