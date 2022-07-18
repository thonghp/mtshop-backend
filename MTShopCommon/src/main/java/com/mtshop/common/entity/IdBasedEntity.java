package com.mtshop.common.entity;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class IdBasedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
}