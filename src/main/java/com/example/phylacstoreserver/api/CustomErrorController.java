package com.example.phylacstoreserver.api;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;

@Controller
public class CustomErrorController {

	@RequestMapping("/error")
	public ResponseEntity<?> handleError(HttpServletRequest request) throws IOException {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				ClassPathResource classPathResource = new ClassPathResource("/static/okak.jpg");
				byte[] bytes = Files.readAllBytes(classPathResource.getFile().toPath());

				return ResponseEntity
					.status(404)
					.contentType(MediaType.IMAGE_JPEG)
					.body(bytes);
			}
		}

		return ResponseEntity
			.status(500)
			.contentType(MediaType.TEXT_PLAIN)
			.body("Произошла ошибка.");
	}
}
