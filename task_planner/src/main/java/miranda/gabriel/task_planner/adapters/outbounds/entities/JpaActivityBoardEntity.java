package miranda.gabriel.task_planner.adapters.outbounds.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "activity_board")
@Data
public class JpaActivityBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private JpaUserEntity owner;

    private JpaImageEntity image;

    @Column(nullable = false)
    private LocalTime messageTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<JpaTaskEntity> tasks;
}
