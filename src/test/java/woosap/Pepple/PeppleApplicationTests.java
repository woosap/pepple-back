package woosap.Pepple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woosap.Pepple.entity.TestEntity;
import woosap.Pepple.repository.TestRepository;

@SpringBootTest
class PeppleApplicationTests {

	@Autowired
	TestRepository testRepository;

	@Test
	void dbConnectionTest() {
		TestEntity testEntity = new TestEntity();
		Long testNum = 2L;
		testEntity.setId(testNum);
		testRepository.save(testEntity);
		testRepository.flush();
		TestEntity result = testRepository.findById(testNum).get();
		Assertions.assertEquals(testEntity.getId(), result.getId());
	}

}
