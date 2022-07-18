package com.mtshop.common.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends IdBasedEntity {

    @Column(length = 45, nullable = false, unique = true)
    private String name;

    @Column(length = 128, nullable = false)
    private String logo;

    @ManyToMany
    @JoinTable(
            name = "brand_category",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Brand() {
    }

    public Brand(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Brand{" + "id=" + id + ", name='" + name + '\'' + ", categories=" + categories + '}';
    }

    @Transient
    public String getLogoPath() {
        if (id == null || logo == null) return "/images/image-thumbnail.png";

        return "/images/brand-images/" + this.id + "/" + this.logo;
    }
}
