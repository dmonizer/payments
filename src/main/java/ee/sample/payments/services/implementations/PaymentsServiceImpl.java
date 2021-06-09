package ee.sample.payments.services.implementations;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.fees.FeeDto;
import ee.sample.payments.domain.payments.*;
import ee.sample.payments.domain.validators.PaymentValidator;
import ee.sample.payments.exceptions.*;
import ee.sample.payments.services.Payment;
import ee.sample.payments.services.PaymentService;
import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentService {
  private static Logger LOG = LoggerFactory.getLogger(PaymentsServiceImpl.class);
  private final AsyncHttpClient httpClient;
  private final PaymentRepository paymentRepository;

  public PaymentsServiceImpl(AsyncHttpClient httpClient, PaymentRepository paymentRepository) {
    this.httpClient = httpClient;
    this.paymentRepository = paymentRepository;
  }

  @Override
  public PaymentDto makePayment(PaymentDto paymentDto) throws PaymentInvalidError {
    try {
      dummySlowHttpRequest(paymentDto);
    }
    catch (InterruptedException  e) {
      LOG.error("Error getting slow response: ",e);
    }

    logTrace("makePayment: payment=%s", paymentDto);
    var payment = new Payment(paymentDto.toEntity());
    if (!new PaymentValidator(payment.getPaymentEntity()).isValid()) {
      throw new PaymentInvalidError("Payment invalid");
    }
    paymentRepository.save(payment.getPaymentEntity());
    return new PaymentDto(payment.getPaymentEntity());
  }

  @Override
  public List<PaymentDto> getPaymentsByDebtor(IBAN debtorIban) throws PaymentNotFoundException {

    logTrace("getPaymentsByDeptor: debtorIban=%s", debtorIban);

    List<PaymentEntity> paymentEntities = paymentRepository.findByDebtorIban(debtorIban);
      if (paymentEntities.isEmpty()) {
        throw new PaymentNotFoundException(String.format("No payments with deptor iban %s found", debtorIban.getIban()));
      }

    return paymentEntities
      .stream().map(PaymentDto::new).collect(Collectors.toList());
  }

  @Override
  public List<PaymentDto> getPaymentsByCreditor(IBAN creditorIban) throws PaymentNotFoundException {
    logTrace("getPaymentsByCreditor: creditorIban=%s", creditorIban);

    List<PaymentEntity> paymentEntities = paymentRepository.findByCreditorIban(creditorIban);
    if (paymentEntities.isEmpty()) {
      throw new PaymentNotFoundException(String.format("No payments with creditor iban %s found", creditorIban.getIban()));
    }
    return paymentEntities
      .stream()
      .map(PaymentDto::new)
      .collect(Collectors.toList());

  }

  @Override
  public PaymentDto getPaymentById(Long id) throws PaymentNotFoundException {
    PaymentEntity payment = paymentRepository.findById(id)
      .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    return new PaymentDto(payment);
  }

  @Transactional // to make sure that all operations (are successful, or all are rolled back if one fails (either payment cacellation or fee creation
  @Override
  public FeeDto cancelPayment(Long id) throws PaymentNotFoundException, PaymentCancellationError {
    logTrace("cancelPayment: id=%s", id);


    PaymentEntity paymentEntity = paymentRepository
      .findById(id)
      .orElseThrow(() -> {
        logTrace("payment (id=%s) not found. ");
        return new PaymentNotFoundException(String.format("Payment with id %s not found", id));
      });

    paymentEntity.cancelled(true);

    logTrace("Payment %s",paymentEntity);
    return new FeeDto(new Payment(paymentEntity).calculateCancellationFee());
  }

  private void logTrace(String messageTemplate, Object... arguments) {
    if (LOG.isTraceEnabled()) {
      LOG.trace(String.format(messageTemplate, arguments));
    }
  }

  private void dummySlowHttpRequest(PaymentDto paymentDto) throws InterruptedException {
    LOG.debug("getting country");
    BoundRequestBuilder getRequest = httpClient.prepareGet("http://localhost:8080/getCountrySlowly");
    getRequest.execute(new AsyncCompletionHandler<String>() {
      @Override
      public String onCompleted(Response response) throws Exception {
        LOG.debug(String.format("Got country: %s",response));
        return "done";
      }
    });

  }

}