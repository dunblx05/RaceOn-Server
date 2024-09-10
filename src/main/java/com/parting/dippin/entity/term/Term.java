package com.parting.dippin.entity.term;

import com.parting.dippin.entity.term.enums.TermType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "term")
@Entity
public class Term {

    @Id
    @Column(name = "term_id", columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer termId;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_type", columnDefinition = "varchar(100)", nullable = false, updatable = false)
    private TermType termType;

    @Column(name = "required", columnDefinition = "boolean default 0", nullable = false)
    private Boolean required;

    @Column(name = "description", columnDefinition = "varchar(100)")
    private String description;

    @Column(name = "term_url", columnDefinition = "TEXT")
    private String termUrl;

    @Column(name = "is_active", columnDefinition = "boolean default 1", nullable = false)
    private Boolean isActive;
}
