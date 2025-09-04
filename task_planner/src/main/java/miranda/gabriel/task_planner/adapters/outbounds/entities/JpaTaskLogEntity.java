package miranda.gabriel.task_planner.adapters.outbounds.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private JpaTaskEntity task;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", unique = true)
    private JpaImageEntity image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private JpaAddressEntity address;

    private String description;

    @Column(nullable = false)
    private LocalDateTime logTime;

    @Column(nullable = false)
    private Integer points;
}
