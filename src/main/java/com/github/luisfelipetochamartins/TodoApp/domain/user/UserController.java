package com.github.luisfelipetochamartins.TodoApp.domain.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	private final UserRepository repository;

	@Autowired
	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public ResponseEntity create(@RequestBody User user) {
		var userByName = repository.findByUsername(user.getUsername());

		if (userByName != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existente");
		}

		var hashedPassword = BCrypt.withDefaults()
				.hashToString(12, user.getPassword().toCharArray());

		user.setPassword(hashedPassword);

		var userCreated = repository.save(user);
		return ResponseEntity.ok(userCreated);
	}
}
