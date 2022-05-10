package ee.sample.payments.services;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.fees.FeeDto;
import ee.sample.payments.domain.payments.PaymentDto;
import ee.sample.payments.exceptions.PaymentInvalidError;

import java.util.List;

public interface PaymentService {
    PaymentDto makePayment(PaymentDto payment) throws PaymentInvalidError;
    List<PaymentDto> getPaymentsByDebtor(IBAN debtorIban);
    List<PaymentDto> getPaymentsByCreditor(IBAN creditorIban);
    PaymentDto getPaymentById(Long id);
    FeeDto cancelPayment(Long id);
}
