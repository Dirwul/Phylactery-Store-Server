package tech.dirwul.phylacterystoreserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.dirwul.phylacterystoreserver.data.model.User;
import tech.dirwul.phylacterystoreserver.data.model.UserCredentials;
import tech.dirwul.phylacterystoreserver.data.repo.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

	public static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final StorageService storageService;

	@Autowired
	public UserService(UserRepository userRepository, StorageService storageService) {
		this.userRepository = userRepository;
		this.storageService = storageService;
	}

	@Transactional
	public String loginUser(UserCredentials credentials) {
		Optional<User> userOpt = userRepository.findByUsernameAndPassword(
			credentials.username(), credentials.password());
		if (userOpt.isPresent()) {
			return userOpt.get().getToken();
		}

		User user = new User(credentials);
		user = userRepository.save(user); // init user
		storageService.setupUserStorage(user.getId());
		return user.getToken();
	}

	@Transactional
	public UUID getUserUUID(String token) {
		return userRepository.findByToken(token).get().getId();
	}
}
