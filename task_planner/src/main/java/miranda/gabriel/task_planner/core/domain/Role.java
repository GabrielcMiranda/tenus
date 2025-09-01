package miranda.gabriel.task_planner.core.domain;

public class Role {

    public Role(Long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    private final Long id;

    private RoleName roleName;

    private enum RoleName{
        REGULAR,
        ADMIN
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
