package com.spring.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "car_brand", schema = "public")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="car_model", joinColumns=@JoinColumn(name="brand_id"))
    private List<String> model = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getModel() {
        return model;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }
}
