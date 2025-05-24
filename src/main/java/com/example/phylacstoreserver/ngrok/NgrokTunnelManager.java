package com.example.phylacstoreserver.ngrok;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NgrokTunnelManager {

	private static final Logger log = LoggerFactory.getLogger(NgrokTunnelManager.class);

	@Value("${server.port}")
	private int port;

	private Process p;

	@PostConstruct
	public void start() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(
			"ngrok",
			"http",
			"--url=balanced-egret-discrete.ngrok-free.app",
			String.valueOf(port));

		p = pb.start();
	}

	@PreDestroy
	public void stop() {
		p.destroy();
	}
}
