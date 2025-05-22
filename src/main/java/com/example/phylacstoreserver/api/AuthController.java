package com.example.phylacstoreserver.api;

import com.example.phylacstoreserver.api.dto.UserDTO;
import com.example.phylacstoreserver.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/sign_up")
	public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
		return authService.signUp(userDTO)
			.map(token -> ResponseEntity.status(HttpStatus.CREATED).body(token))
			.orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
		return authService.login(userDTO)
			.map(token -> ResponseEntity.ok().body(token))
			.orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
