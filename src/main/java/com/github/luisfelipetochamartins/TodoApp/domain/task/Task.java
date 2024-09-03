package com.github.luisfelipetochamartins.TodoApp.domain.task;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 50)
	private String title;
	private String description;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;
	private Integer userId;
	@CreationTimestamp
	private LocalDateTime createdAt;

	public Task() {}

	public Task(Integer id, String title, String description, LocalDateTime startAt, LocalDateTime endAt, String priority, Integer userId, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.priority = priority;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws Exception {
		if (title.length() > 50) {
			throw new Exception("O Campo title deve ter no máximo 50 caracteres");
		}
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("id = ").append(id);
		sb.append(", title = ").append(title);
		sb.append(", description = ").append(description);
		sb.append(", startAt = ").append(startAt);
		sb.append(", endAt = ").append(endAt);
		sb.append(", priority = ").append(priority);
		sb.append(", userId = ").append(userId);
		sb.append(", createdAt = ").append(createdAt);
		return sb.toString();
	}
}
