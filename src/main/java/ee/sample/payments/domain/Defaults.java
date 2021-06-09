package ee.sample.payments.domain;

import java.util.Currency;

public class Defaults {
  public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");
  private Defaults() {
    // non instantiable constant-holder class
  }
}
