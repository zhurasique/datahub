package com.space.datahub.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "name"})
public class ProductInBag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    Bag bag;

    @ManyToOne
    Product product;

    public ProductInBag () {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
