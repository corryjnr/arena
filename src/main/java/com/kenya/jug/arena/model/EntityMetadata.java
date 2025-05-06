package com.kenya.jug.arena.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Embeddable
public class EntityMetadata {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    private boolean archived;
    private String version;
    private Long createdBy;
}
