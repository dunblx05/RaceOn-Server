package com.parting.dippin.entity.member;

import com.parting.dippin.entity.member.enums.Gender;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.member.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class Member {

    @Id
    @Column(name = "member_id", columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(name = "nickname", columnDefinition = "varchar(20)", unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "char(6) default 'MALE'", nullable = false)
    private Gender gender;

    @Column(name = "birth_date", columnDefinition = "date")
    private Date birthDate;

    @Column(name = "profile_image_url", columnDefinition = "text")
    private String profileImageUrl;

    @Column(name = "phone_number", columnDefinition = "varchar(30)")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "char(15) default 'NORMAL'", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", columnDefinition = "char(30) default 'ACTIVE'", nullable = false)
    private MemberStatus memberStatus;

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "datetime")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime currentAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        this.createdAt = currentAt;
        this.updatedAt = currentAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
