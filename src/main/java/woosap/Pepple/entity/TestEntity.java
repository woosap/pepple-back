package woosap.Pepple.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class TestEntity {
    @Id
    private Long id;
}
