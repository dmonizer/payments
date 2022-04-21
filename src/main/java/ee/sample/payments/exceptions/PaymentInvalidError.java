package ee.sample.payments.exceptions;

public class PaymentInvalidError extends Throwable {
    public PaymentInvalidError(String message) {
        super(message);
    }
}
