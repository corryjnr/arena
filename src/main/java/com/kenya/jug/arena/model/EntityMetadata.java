package com.kenya.jug.arena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class EntityMetadata {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private boolean archived;
    private String version;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;
}
