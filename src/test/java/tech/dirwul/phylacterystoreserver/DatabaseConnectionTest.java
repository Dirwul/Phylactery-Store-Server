package tech.dirwul.phylacterystoreserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class DatabaseConnectionTest {

	private static final Logger log = LoggerFactory.getLogger(DatabaseConnectionTest.class);

	@Autowired
	private DataSource dataSource;

	private String db_user;
	private String db_password;

	@BeforeAll
	static void logging(
		@Value("${spring.datasource.username}") String user,
		@Value("${spring.datasource.password}") String password
	) {
		log.warn("Autowired:");
		log.warn("Database user: {}", user);
		log.warn("Database password: {}", password);
	}

	@Test
	void contextLoads() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			assertFalse("Connection should be open", conn.isClosed());
		}
	}

	@Test
	void testDatabaseConfig() {
		assertTrue("DB_USER not matching with correct!", db_user.equals("dirwul"));
	}

}

