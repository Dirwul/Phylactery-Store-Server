package tech.dirwul.phylacterystoreserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest
public class DatabaseConnectionTest {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			assertFalse("Connection should be open", conn.isClosed());
		}
	}
}

