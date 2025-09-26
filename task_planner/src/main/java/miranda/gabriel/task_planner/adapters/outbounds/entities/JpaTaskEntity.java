package miranda.gabriel.task_planner.adapters.outbounds.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import miranda.gabriel.task_planner.core.enums.TaskStatus;

@Entity
@Table(name = "task")
@Data
public class JpaTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private JpaActivityBoardEntity board;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", unique = true)
    private JpaImageEntity image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private JpaAddressEntity address;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime; 

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private Boolean completed;

    @OneToMany(
        mappedBy = "task"
    )
    private List<JpaTaskLogEntity> taskLogs;
}
