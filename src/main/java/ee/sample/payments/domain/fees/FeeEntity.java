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

    // IRL I would not tie the fee directly to a single payment, rather to something more generic
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PaymentEntity.class, optional = false)
    PaymentEntity payment;

}
