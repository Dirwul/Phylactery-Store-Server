package tech.dirwul.phylacterystoreserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.dirwul.phylacterystoreserver.data.model.UserCredentials;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {

	public static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final UserService userService;

	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
		return ResponseEntity.ok(userService.loginUser(credentials));
	}

	@GetMapping("/uuid")
	public ResponseEntity<UUID> getUUID(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String token =  authHeader.substring(7); // remove 'Bearer '
		return ResponseEntity.ok(userService.getUserUUID(token));
	}
}
