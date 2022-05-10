package ee.sample.payments.exceptions;

public class PaymentCancellationError extends RuntimeException {
    public PaymentCancellationError(String message) {
        super(message);
    }
}
