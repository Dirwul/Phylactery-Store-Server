package com.example.phylacstoreserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScriptRunner {

	private static final Logger log = LoggerFactory.getLogger(ScriptRunner.class);

	public static void runFromResource(String scriptName, String... args) {
		try {
			Path tmpScript = Files.createTempFile("tmp_script", ".sh");

			// copy script
			try (InputStream in = ScriptRunner.class.getResourceAsStream("/static/" + scriptName)) {
				if (in == null) {
					throw new FileNotFoundException(scriptName);
				}
				Files.copy(in, tmpScript, StandardCopyOption.REPLACE_EXISTING);
			}

			// set script executable
			tmpScript.toFile().setExecutable(true);

			// build command
			String[] cmd = new String[args.length + 2];
			cmd[0] = "sudo";
			cmd[1] = tmpScript.toAbsolutePath().toString();
			System.arraycopy(args, 0, cmd, 2, args.length);

			ProcessBuilder pb = new ProcessBuilder(cmd);
			pb.inheritIO();
			Process p = pb.start();
			int exitCode = p.waitFor();
			log.info("Script {} exit code: {}", scriptName, exitCode);

			Files.deleteIfExists(tmpScript);
		} catch (IOException e) {
			log.error("Error copying script {}: {}", scriptName, e.getMessage());
		} catch (InterruptedException e) {
			log.error("Error running script {}: {}", scriptName, e.getMessage());
		}
	}
}
