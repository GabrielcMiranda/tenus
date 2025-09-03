package miranda.gabriel.task_planner.core.domain.model;

import java.time.LocalDateTime;

public class TaskLog {

    

    public TaskLog(Long id, Task task, Image image, Address address, String description, LocalDateTime logTime,
            Integer points) {
        this.id = id;
        this.task = task;
        this.image = image;
        this.address = address;
        this.description = description;
        this.logTime = logTime;
        this.points = points;
    }

    private final Long id;

    private Task task;

    private Image image;

    private Address address;

    private String description;

    private LocalDateTime logTime;

    private Integer points;

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
