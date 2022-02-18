package com.will.Food4Thought.chef;

import java.util.Objects;

public class Chef {
    private Integer id;
    private String name;
    private String email;
    private String location;
    private Double price;

    public Chef() {
    }

    public Chef(String name, String email, String location, Double price) {
        this.id = null;
        this.name = name;
        this.email = email;
        this.location = location;
        this.price = price;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Chef{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return Objects.equals(id, chef.id) && Objects.equals(name, chef.name) && Objects.equals(email, chef.email) && Objects.equals(location, chef.location) && Objects.equals(price, chef.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, location, price);
    }
}
