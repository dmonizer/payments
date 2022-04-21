package ee.sample.payments.domain.payments;

import ee.sample.payments.domain.IBAN;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PaymentRepository extends PagingAndSortingRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByDebtorIban(IBAN aLong);

    List<PaymentEntity> findByCreditorIban(IBAN aLong);
}
