package woosap.Pepple.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woosap.Pepple.entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, String> {

   Optional<TestEntity> findById(Long id);

}
