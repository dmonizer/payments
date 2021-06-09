package ee.sample.payments.services;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.fees.FeeDto;
import ee.sample.payments.domain.payments.PaymentDto;
import ee.sample.payments.exceptions.*;

import java.util.List;

public interface PaymentService {
  PaymentDto makePayment(PaymentDto payment) throws PaymentInvalidError;

  List<PaymentDto> getPaymentsByDebtor(IBAN debtorIban) throws PaymentNotFoundException;

  List<PaymentDto> getPaymentsByCreditor(IBAN creditorIban) throws PaymentNotFoundException;

  PaymentDto getPaymentById(Long id) throws PaymentNotFoundException;

  FeeDto cancelPayment(Long id) throws PaymentNotFoundException, PaymentCancellationError;
}
