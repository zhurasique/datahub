package com.space.datahub.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "name"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    private Type type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
