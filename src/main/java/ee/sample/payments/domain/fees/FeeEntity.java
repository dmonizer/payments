package ee.sample.payments.domain.fees;

import ee.sample.payments.domain.BaseEntity;
import ee.sample.payments.domain.payments.PaymentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "FEES")
@Setter
@Getter
@Accessors(fluent = true)
public class FeeEntity extends BaseEntity {

  @Column
  BigDecimal amount;

  @Column
  Currency currency;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = PaymentEntity.class, optional = false)
  PaymentEntity payment;

}
