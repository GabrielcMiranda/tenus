package miranda.gabriel.tenus.adapters.outbounds.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import miranda.gabriel.tenus.core.enums.UserRole;
import miranda.gabriel.tenus.adapters.outbounds.entities.embedded.EmailEmbeddable;
import miranda.gabriel.tenus.adapters.outbounds.entities.embedded.PhoneEmbeddable;

@Entity
@Table(name = "users")
@Data
public class JpaUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Embedded
    @AttributeOverride(
        name = "value",
        column = @Column(name = "email", nullable = false, unique = true)
    )
    private EmailEmbeddable email;

    @Column(nullable = false)
    private String password;

    @Embedded
    @AttributeOverride(
        name = "value",
        column = @Column(name = "phone", nullable = false, unique = true)
    )
    private PhoneEmbeddable phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalTime messageTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer score;

    @OneToMany(
        mappedBy = "owner",
        cascade = CascadeType.ALL,
        orphanRemoval = true )
    private List<JpaActivityBoardEntity> boards = new ArrayList<>(); 

    public boolean isLoginCorrect(String rawPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
