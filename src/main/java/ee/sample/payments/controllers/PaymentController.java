package ee.sample.payments.controllers;

import ee.sample.payments.domain.IBAN;
import ee.sample.payments.domain.fees.FeeDto;
import ee.sample.payments.domain.payments.PaymentDto;
import ee.sample.payments.exceptions.*;
import ee.sample.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.*;

@RestController
public class PaymentController { // IRL I would document endpoints and parameters with swagger accessible only when running on dev profile.
  private final PaymentService service;

  @Autowired
  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PaymentDto createPayment(@RequestBody PaymentDto payment) throws PaymentInvalidError {
    return service.makePayment(payment);
  }

  @PostMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public FeeDto cancelPayment(@PathVariable Long id) throws PaymentCancellationError, PaymentNotFoundException {
    return service.cancelPayment(id);
  }

  @GetMapping(path = "/bydebtor/{iban}")
  public List<PaymentDto> getPaymentsByDebtor(@PathVariable(name = "iban") IBAN debtorIban) throws PaymentNotFoundException {
    return service.getPaymentsByDebtor(debtorIban);
  }

  @GetMapping(path = "/bycreditor/{iban}")
  public List<PaymentDto> getPaymentsByCreditor(@PathVariable(name = "iban") IBAN creditorIban) throws PaymentNotFoundException {
    return service.getPaymentsByCreditor(creditorIban);
  }

  @GetMapping(path = "/byid/{id}")
  public PaymentDto getPaymentById(@PathVariable(name = "id") Long id) throws PaymentNotFoundException {
    return service.getPaymentById(id);
  }

  @GetMapping(path = "/getCountrySlowly")
  public String mockCountry() throws ExecutionException, InterruptedException {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    return executorService.schedule(this::getCountry, 5, TimeUnit.SECONDS).get(); // return response in 5 seconds to emulate slow external API
  }

  private String getCountry() {
    return "EE";
  }

}
