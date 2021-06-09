package ee.sample.payments.domain;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.List;

@Data
public class IBAN {
  private static final List<String> VALID_COUNTRIES = List.of("EE", "LV", "LT");
  String ibanValue;
  public IBAN(String iban) {
    Assert.notNull(iban, "Cannot create empty IBAN");
    Assert.isTrue(isValidIban(iban), "Only Baltic payments allowed");
    this.ibanValue = iban;
  }

  public String getIban() {
    return ibanValue;
  }

  private boolean isValidIban(String iban) {
    return VALID_COUNTRIES.contains(iban.toUpperCase().substring(0, 2));
  }
}
