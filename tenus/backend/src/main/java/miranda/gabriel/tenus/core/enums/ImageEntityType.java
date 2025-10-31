package miranda.gabriel.tenus.core.enums;

public enum ImageEntityType {
    BOARD("boards"),
    TASK("tasks"),
    TASK_LOG("task-logs");

    private final String folderName;

    ImageEntityType(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
