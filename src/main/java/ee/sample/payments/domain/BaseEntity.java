package ee.sample.payments.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@Accessors(fluent = true)
public class BaseEntity implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return (id == null || id == 0);
    }
}
