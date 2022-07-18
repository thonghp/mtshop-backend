package com.mtshop.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Data
public class Role extends IdBasedEntity {

    @Column(length = 40, nullable = false, unique = true) // varchar 40, not null
    private String name;

    @Column(length = 150, nullable = false)
    private String description;


    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String description) {
        this.description = description;
        this.name = name;
    }
}
