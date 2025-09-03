package miranda.gabriel.task_planner.adapters.outbounds.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "task_log")
@Data
public class JpaTaskLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tasklog_id")
    private Long id;

    @Column(nullable = false)
    private JpaTaskEntity task;

    @Column(nullable = false, unique = true)
    private JpaImageEntity image;

    @Column(nullable = false)
    private JpaAddressEntity address;

    private String description;

    @Column(nullable = false)
    private LocalDateTime logTime;

    @Column(nullable = false)
    private Integer points;
}
