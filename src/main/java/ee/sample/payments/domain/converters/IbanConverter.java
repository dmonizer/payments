package ee.sample.payments.domain.converters;

import ee.sample.payments.domain.IBAN;

import javax.persistence.AttributeConverter;

public class IbanConverter implements AttributeConverter<IBAN, String> {
  @Override
  public String convertToDatabaseColumn(IBAN attribute) {
    return attribute.getIban();
  }

  @Override
  public IBAN convertToEntityAttribute(String dbData) {
    return new IBAN(dbData);
  }
}
