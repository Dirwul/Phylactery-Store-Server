package tech.dirwul.phylacterystoreserver.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.dirwul.phylacterystoreserver.data.model.User;
import tech.dirwul.phylacterystoreserver.data.model.UserCredentials;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsernameAndPassword(String username, String password);

	Optional<User> findByToken(String token);
}
