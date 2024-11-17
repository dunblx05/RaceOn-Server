package com.parting.dippin.core.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime")
    protected LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "datetime")
    protected LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
