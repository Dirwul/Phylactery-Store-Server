package com.example.phylacstoreserver.core;

import com.example.phylacstoreserver.data.entity.User;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

	public void init(User user) {
		initSystemUser(user);
		initTimemachineStore(user);
	}

	private void initSystemUser(User user) {
		ScriptRunner.runFromResource(
			"create_system_user.sh",
			user.getUsername()
		);
	}

	private void initTimemachineStore(User user) {
		ScriptRunner.runFromResource(
			"setup_macos_share.sh",
			user.getUsername(),
			user.getPassword()
		);
	}
}
