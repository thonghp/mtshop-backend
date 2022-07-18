package com.mtshop.common.entity;

import javax.persistence.*;

@Entity
@Table(name = "states")
public class State extends IdBasedEntity {

    @Column(length = 45, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public State() {
    }

    public State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "State{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
