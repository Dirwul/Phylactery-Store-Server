package com.example.phylacstoreserver.api;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class NotFoundController implements ErrorController {

	@RequestMapping("/error")
	public void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int statusCode = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (statusCode == 404) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);

			try (InputStream in = getClass().getResourceAsStream("/static/okak.jpg");
				OutputStream out = response.getOutputStream()
			) {
				if (in != null) {
					in.transferTo(out);
				} else {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Resource for 404 not found, lol");
				}
			}
		}
	}
}
