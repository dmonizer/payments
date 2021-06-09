package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.IBAN;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class PaymentDto { // IRL would use MapStruct
  private Long id;
  private PaymentType type;
  private BigDecimal amount;
  private String debtorIban;
  private String creditorIban;
  private Currency currency;
  private String details;
  private String bic;
  private boolean cancelled;

  public PaymentDto() {

  }
  public PaymentDto(PaymentEntity paymentEntity) {
    this.id = paymentEntity.id();
    this.type = paymentEntity.type();
    this.amount = paymentEntity.amount();
    this.debtorIban = paymentEntity.debtorIban().getIban();
    this.creditorIban = paymentEntity.creditorIban().getIban();
    this.currency = paymentEntity.currency();
    this.details = paymentEntity.details();
    this.bic = paymentEntity.BIC();
    this.cancelled = paymentEntity.cancelled();

  }
  public PaymentEntity toEntity() {
    PaymentEntity entity = new PaymentEntity()
      .amount(amount)
      .currency(currency)
      .type(type)
      .debtorIban(new IBAN(debtorIban))
      .creditorIban(new IBAN(creditorIban))
      .details(details)
      .BIC(bic)
      .cancelled(this.cancelled);

    entity.id(id);
    return entity;
  }
}
