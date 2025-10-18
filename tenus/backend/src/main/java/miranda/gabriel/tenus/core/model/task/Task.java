package miranda.gabriel.tenus.core.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import miranda.gabriel.tenus.core.enums.TaskStatus;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.address.Address;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.core.model.task_log.TaskLog;

public class Task {

    public Task(Long id, String name, ActivityBoard board, Image image, Address address, String description,
            LocalDate date, LocalTime startTime, LocalTime endTime, LocalDateTime createdAt, LocalDateTime updatedAt,
            TaskStatus status,Boolean completed) {

        if(startTime.isBefore(board.getOwner().getMessageTime())){
            throw new IllegalArgumentException("Board message time needs to be earlier than any task of the day");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Task cannot end before it starts");
        }

        this.id = id;
        this.name = name;
        this.board = board;
        this.image = image;
        this.address = address;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.completed = completed;
        this.taskLogs = new ArrayList<>();
    }

    private Long id;

    private String name;

    private ActivityBoard board;

    private Image image;

    private Address address;

    private String description;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private TaskStatus status;

    private Boolean completed;

    private List<TaskLog> taskLogs;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityBoard getBoard() {
        return board;
    }

    public void setBoard(ActivityBoard board) {
        this.board = board;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<TaskLog> getTaskLogs() {
        return taskLogs;
    }

    public void setTaskLogs(List<TaskLog> taskLogs) {
        this.taskLogs = taskLogs;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
}
