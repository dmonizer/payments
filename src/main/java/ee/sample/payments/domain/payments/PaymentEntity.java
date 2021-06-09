package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.auditing.Auditable;
import ee.sample.payments.domain.converters.IbanConverter;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PAYMENTS")
public class PaymentEntity extends Auditable {


  @Column(name = "paymenttype")
  @Enumerated(EnumType.ORDINAL)
  private PaymentType type;

  @Column
  private BigDecimal amount;

  @Column(name = "debtoriban")
  @Convert(converter = IbanConverter.class)
  private IBAN debtorIban;

  @Column(name = "creditoriban")
  @Convert(converter = IbanConverter.class)
  private IBAN creditorIban;

  @Column
  private Currency currency;

  @Column
  private String details;

  @Column(name = "BIC")
  private String BIC;

  @Column
  private boolean cancelled;

}

