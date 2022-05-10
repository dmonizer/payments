package ee.sample.payments.exceptions;

public class PaymentInvalidError extends RuntimeException {
    public PaymentInvalidError(String message) {
        super(message);
    }
}
