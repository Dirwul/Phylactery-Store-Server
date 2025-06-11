package tech.dirwul.phylacterystoreserver;

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

	@Value("${spring.datasource.username}")
	private String db_user;
	@Value("${spring.datasource.password}")
	private String db_password;

	@Test
	void contextLoads() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			assertFalse("Connection should be open", conn.isClosed());
		}
	}

	@Test
	void testDatabaseConfig() {
		log.warn("Autowired:");
		log.warn("Database user: {}", this.db_user);
		log.warn("Database password: {}", this.db_password);
		assertTrue("DB_USER not matching with correct!", db_user.equals("dirwul"));
	}

}

