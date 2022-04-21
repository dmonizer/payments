package ee.sample.payments.domain;

import ee.sample.payments.domain.payments.PaymentType;

public class PaymentCancellationFeeCoefficients {
    public static final double TYPE_1 = 0.05;
    public static final double TYPE_2 = 0.1;
    public static final double TYPE_3 = 0.15;

    private PaymentCancellationFeeCoefficients() {
        // non-instantiable utility class
    }

    public static double getCancellationFeeCoefficient(PaymentType type) {
        switch (type) {
            case TYPE_1:
                return TYPE_1;
            case TYPE_2:
                return TYPE_2;
            case TYPE_3:
                return TYPE_3;
            default:
                throw new IllegalArgumentException(String.format("Unknown payment type: %s", type));
        }
    }
}
