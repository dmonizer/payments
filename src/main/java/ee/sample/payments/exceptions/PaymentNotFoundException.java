package ee.sample.payments.exceptions;

public class PaymentNotFoundException extends Exception {
  public PaymentNotFoundException() {
    super();
  }

  public PaymentNotFoundException(String message) {
    super(message);
  }
}
