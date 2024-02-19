package com.inventory.manager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Data
@Entity
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameSystem;
    private String username;
    @Lob
    @Column(name = "logo", columnDefinition = "LONGBLOB")
    private byte[] logo;
}
