package ee.sample.payments.services.implementations;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.fees.FeeDto;
import ee.sample.payments.domain.payments.PaymentDto;
import ee.sample.payments.domain.payments.PaymentEntity;
import ee.sample.payments.domain.payments.PaymentRepository;
import ee.sample.payments.domain.validators.PaymentValidator;
import ee.sample.payments.exceptions.PaymentInvalidError;
import ee.sample.payments.exceptions.PaymentNotFoundException;
import ee.sample.payments.services.Payment;
import ee.sample.payments.services.PaymentService;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentsServiceImpl.class);
    private final AsyncHttpClient httpClient;
    private final PaymentRepository paymentRepository;

    public PaymentsServiceImpl(AsyncHttpClient httpClient, PaymentRepository paymentRepository) {
        this.httpClient = httpClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentDto makePayment(PaymentDto paymentDto) {
        dummySlowHttpRequest();

        logTrace("makePayment: payment=%s", paymentDto);
        var payment = new Payment(paymentDto.toEntity());
        if (!new PaymentValidator(payment.getPaymentEntity()).isValid()) {
            throw new PaymentInvalidError("Payment invalid"); // here I am relying on DB rollback to not save the payment if invalid. IRL would not rely only on that.
        }
        paymentRepository.save(payment.getPaymentEntity());
        return new PaymentDto(payment.getPaymentEntity());
    }

    @Override
    public List<PaymentDto> getPaymentsByDebtor(IBAN debtorIban) {

        logTrace("getPaymentsByDeptor: debtorIban=%s", debtorIban);

        List<PaymentEntity> paymentEntities = paymentRepository.findByDebtorIban(debtorIban);
        if (paymentEntities.isEmpty()) {
            throw new PaymentNotFoundException(String.format("No payments with deptor iban %s found", debtorIban.getIban()));
        }

        return paymentEntities
                .stream()
                .map(PaymentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByCreditor(IBAN creditorIban) {
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
    public PaymentDto getPaymentById(Long id) {
        PaymentEntity payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        return new PaymentDto(payment);
    }

    @Transactional
    // to make sure that all operations (are successful, or all are rolled back if one fails (either payment cacellation or fee creation
    @Override
    public FeeDto cancelPayment(Long id) {
        logTrace("cancelPayment: id=%s", id);

        var paymentEntity = paymentRepository
                .findById(id)
                .orElseThrow(() -> {
                    logTrace("payment (id=%s) not found. ");
                    return new PaymentNotFoundException(String.format("Payment with id %s not found", id));
                });

        paymentEntity.cancelled(true);

        logTrace("Payment %s", paymentEntity);
        return new FeeDto(new Payment(paymentEntity).calculateCancellationFee());
    }

    private void logTrace(String messageTemplate, Object... arguments) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(String.format(messageTemplate, arguments));
        }
    }

    private void dummySlowHttpRequest() {
        LOG.debug("getting country");
        BoundRequestBuilder getRequest = httpClient.prepareGet("http://localhost:8080/getCountrySlowly");
        getRequest.execute(new AsyncCompletionHandler<String>() {
            @Override
            public String onCompleted(Response response) {
                LOG.debug(String.format("Got country: %s", response));
                return "done";
            }
        });

    }
}
