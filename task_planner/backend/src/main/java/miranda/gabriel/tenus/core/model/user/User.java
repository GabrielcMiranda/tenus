package miranda.gabriel.tenus.core.model.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import miranda.gabriel.tenus.core.enums.UserRole;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.vo.Email;
import miranda.gabriel.tenus.core.vo.Phone;

public class User {

    public User(){
        
    }

    private UUID id;

    private String username;

    private Email email;

    private String password;

    private Phone phone;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer score;

    private List<ActivityBoard> boards = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
}
