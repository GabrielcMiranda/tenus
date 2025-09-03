package miranda.gabriel.task_planner.adapters.outbounds.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import miranda.gabriel.task_planner.core.domain.enums.UserRole;
import miranda.gabriel.task_planner.core.domain.vo.Email;
import miranda.gabriel.task_planner.core.domain.vo.Phone;

@Entity
@Table(name = "user")
@Data
public class JpaUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private Email email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private Phone phone;

    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer score;


}
