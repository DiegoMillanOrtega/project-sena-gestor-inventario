package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consecutive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consecutiveId;

    @Column(nullable = false)
    private int consecutive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prefix_id", nullable = false)
    @JsonBackReference
    private Prefix prefix;
}
