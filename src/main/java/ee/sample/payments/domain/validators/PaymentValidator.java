package ee.sample.payments.domain.validators;

import ee.sample.payments.domain.payments.PaymentEntity;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

public class PaymentValidator {
  private PaymentEntity payment;

  public PaymentValidator(PaymentEntity payment) {
    this.payment = payment;
  }

  public boolean isValid() {
    if (!hasPositiveAmount()) {
      return false;
    }

    switch (payment.type()) {
      case TYPE_1: return ("EUR".equals(payment.currency().getCurrencyCode()) && StringUtils.hasText(payment.details()));
      case TYPE_2: return ("USD".equals(payment.currency().getCurrencyCode()));
      case TYPE_3: return (StringUtils.hasText(payment.bic()) && List.of("EUR", "USD").contains(payment.currency().getCurrencyCode()));
      default: return false;
    }
  }

  private boolean hasPositiveAmount() {
    return payment.amount() != null && BigDecimal.ZERO.compareTo(payment.amount()) < 0;
  }
}
