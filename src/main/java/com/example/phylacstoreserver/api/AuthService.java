package com.example.phylacstoreserver.api;

import com.example.phylacstoreserver.api.dto.UserDTO;
import com.example.phylacstoreserver.data.User;
import com.example.phylacstoreserver.data.UserRepository;
import com.example.phylacstoreserver.utils.DTOMapper;
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

	@Autowired
	public AuthService(UserRepository userRepository, DTOMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	public Optional<String> signUp(UserDTO userDTO) {
		if (userRepository.existsByUsername(userDTO.username())) {
			return Optional.empty();
		}

		User user = mapper.toEntity(userDTO);
		String token = UUID.randomUUID().toString();
		user.setToken(token);
		userRepository.save(user);

		// todo: здесь должна вызываться разметка пространства для пользователя

		return Optional.of(token);
	}

	public Optional<String> login(UserDTO userDTO) {
		Optional<User> userOpt = userRepository.findByUsername(userDTO.username());
		if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(userDTO.password())) {
			// validate not empty and matching password
			return Optional.empty();
		}
		return Optional.of(userOpt.get().getToken());
	}
}
