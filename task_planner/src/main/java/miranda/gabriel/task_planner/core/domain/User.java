package miranda.gabriel.task_planner.core.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    public User(UUID id, String username, String email, String password, String phone, Role role,
            LocalDateTime createdAt, LocalDateTime updatedAt, List<ActivityBoard> boards) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.boards = boards;
    }

    private final UUID id;

    private String username;

    private String email;

    private String password;

    private String phone;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ActivityBoard> boards = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ActivityBoard> getBoards() {
        return boards;
    }

    public void setBoards(List<ActivityBoard> boards) {
        this.boards = boards;
    }
    
}
