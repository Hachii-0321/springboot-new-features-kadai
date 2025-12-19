package com.example.samuraitravel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="favorites")
@Data
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name= "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
}
