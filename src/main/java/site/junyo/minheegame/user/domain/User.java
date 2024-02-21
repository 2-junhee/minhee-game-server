package site.junyo.minheegame.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.junyo.minheegame.common.BaseTimeEntity;

import java.util.UUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@ToString
@Entity
@Table(name = "user_info")
@SequenceGenerator(name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_INFO_IDX_SEQ",
        initialValue = 1, allocationSize = 1)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR")
    private Long idx;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, length = 20, unique = true)
    private String id;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Builder
    public User(Long idx, UUID uuid, String id, String password, String nickname) {
        this.idx = idx;
        this.uuid = uuid;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }
}

