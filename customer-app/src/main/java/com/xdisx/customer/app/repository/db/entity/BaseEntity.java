package com.xdisx.customer.app.repository.db.entity;

import com.xdisx.customer.app.repository.db.listener.AuditListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract  class BaseEntity {
    @NotNull
    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @NotNull @Column(name = "modified")
    private LocalDateTime modified;

    @NotNull @Version
    private long version;

    public abstract BigInteger getId();
    public abstract void setId(BigInteger id);


    @Override
    public int hashCode() {
        return idVersionCalc(this).hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity entity)) return false;
        return getId() != null && idVersionCalc(this).equals(idVersionCalc(entity));
    }

    private String idVersionCalc(BaseEntity entity) {
        return String.format("%d_%d", entity.getId(), entity.getVersion());
    }
}
