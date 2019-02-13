package com.spring.application.repo;

import com.spring.application.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarBrandRepo extends CrudRepository<CarBrand, Integer> {

    CarBrand getCarBrandById (int id);
    List<CarBrand> findAll();
    CarBrand findByName(String name);
}