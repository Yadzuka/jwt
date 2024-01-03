package ru.yadzuka.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "role", schema = "public")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
