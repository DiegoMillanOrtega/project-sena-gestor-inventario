package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prefix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long prefixId;

    @Column(length = 5, nullable = false)
    private String prefix;

    @OneToMany(mappedBy = "prefix", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Consecutive> consecutive = new ArrayList<>();
}
