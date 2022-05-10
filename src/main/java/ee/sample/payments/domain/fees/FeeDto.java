package ee.sample.payments.domain.fees;

import ee.sample.payments.domain.payments.PaymentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@NoArgsConstructor // needed for Jackson
public class FeeDto { // IRL would use MapStruct
    Long id;
    BigDecimal amount;
    Currency currency;
    PaymentDto payment;

    public FeeDto(FeeEntity fee) {
        this.id = fee.id();
        this.amount = fee.amount();
        this.currency = fee.currency();
        this.payment = new PaymentDto(fee.payment());
    }

    public FeeEntity toEntity() {
        var feeEntity = new FeeEntity()
                .amount(amount)
                .currency(currency)
                .payment(this.payment.toEntity());

        feeEntity.id(id);
        return feeEntity;
    }


}
