package ee.sample.payments.exceptions;

public class PaymentCancellationError extends Throwable {
    public PaymentCancellationError(String message) {
        super(message);
    }
}
