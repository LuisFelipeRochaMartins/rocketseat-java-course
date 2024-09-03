package com.github.luisfelipetochamartins.TodoApp.domain.task;

import com.github.luisfelipetochamartins.TodoApp.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

	private final TaskRepository repository;

	@Autowired
	public TaskController(TaskRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public ResponseEntity<List<Task>> getAllFromUser(HttpServletRequest request) {
		var userId = (Integer) request.getAttribute("userId");
		var tasks = repository.findAllByUserId(userId);

		return ResponseEntity.ok(tasks);
	}

	@PostMapping
	public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {
		task.setUserId((Integer) request.getAttribute("userId"));

		var currentDate = LocalDateTime.now();
		if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
			return ResponseEntity.badRequest()
					.body("A data de início ou térmico deve ser maior do que a data atual");
		}

		if (task.getStartAt().isAfter(task.getEndAt())) {
			return ResponseEntity.badRequest()
					.body("A data de início deve ser antes da data de término");
		}

		var createdTask = repository.save(task);

		return ResponseEntity.ok(createdTask);
	}


	@PutMapping(path = "/{id}")
	public ResponseEntity update(@RequestBody Task taskModel, HttpServletRequest request, @PathVariable Integer id) {
		var userId = (Integer) request.getAttribute("userId");
		var task =  repository.findById(id).orElse(null);

		if (task == null) {
			return ResponseEntity.badRequest()
					.body("Tarefa não encontrada");
		}

		if (!task.getUserId().equals(userId)) {
			return ResponseEntity.badRequest()
					.body("Usuário não tem permissão para alterar esta tarefa!");
		}

		Utils.copyNonNullProperties(taskModel, task);

		var updatedTask = repository.save(task);

		return ResponseEntity.ok(updatedTask);
	}

}
