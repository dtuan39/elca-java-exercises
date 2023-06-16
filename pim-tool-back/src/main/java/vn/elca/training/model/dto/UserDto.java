package vn.elca.training.model.dto;

import java.util.List;

/**
 * @author gtn
 */
public class UserDto {
    private Long id;
    private String username;
    private List<TaskDto> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
