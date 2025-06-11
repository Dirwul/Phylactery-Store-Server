package tech.dirwul.phylacterystoreserver;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DatabaseConnectionTest {

	private static final Logger log = LoggerFactory.getLogger(DatabaseConnectionTest.class);


	@BeforeAll
	static void logging(
		@Value("${spring.datasource.username}") String user,
		@Value("${spring.datasource.password}") String password
	) {
		log.warn("Autowired:");
		log.warn("Database user: {}", user);
		log.warn("Database password: {}", password);
	}

}

