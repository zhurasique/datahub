package com.space.datahub.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "name"})
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    private Product product;

    protected ProductImage() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
