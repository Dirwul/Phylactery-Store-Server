package com.example.phylacstoreserver.core;

import com.example.phylacstoreserver.data.dto.UserDTO;
import com.example.phylacstoreserver.data.entity.User;
import com.example.phylacstoreserver.data.repo.UserRepository;
import com.example.phylacstoreserver.data.dto.DTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

	private final static Logger log = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;
	private final DTOMapper mapper;
	private final StoreService storeService;

	@Autowired
	public AuthService(
		UserRepository userRepository,
		DTOMapper mapper,
		StoreService storeService
	) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.storeService = storeService;
	}

	public Optional<String> signUp(UserDTO userDTO) {
		if (userRepository.existsByUsername(userDTO.username())) {
			return Optional.empty();
		}

		// create entity & generate token
		User user = mapper.toEntity(userDTO);
		user.setToken(generateToken());
		userRepository.save(user);

		// init user on system
		storeService.init(user);

		return Optional.of(user.getToken());
	}

	public Optional<String> login(UserDTO userDTO) {
		Optional<User> userOpt = userRepository.findByUsername(userDTO.username());
		if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(userDTO.password())) {
			// validate not empty and matching password
			return Optional.empty();
		}
		return Optional.of(userOpt.get().getToken());
	}

	private String generateToken() {
		return UUID.randomUUID().toString();
	}
}
