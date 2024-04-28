package com.xdisx.customer.app.repository.db.listener;

import com.xdisx.customer.app.repository.db.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    public void onCreate(BaseEntity entity) {
        entity.setCreated(LocalDateTime.now());
        entity.setModified(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(BaseEntity entity) {
        entity.setModified(LocalDateTime.now());
    }
}
