package miranda.gabriel.tenus.core.model.activity_board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.core.model.task.Task;
import miranda.gabriel.tenus.core.model.user.User;

public class ActivityBoard {

    public ActivityBoard() {
        this.tasks = new ArrayList<>();
    }

    private Long id;

    private String name;

    private User owner;

    private Image image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
}
