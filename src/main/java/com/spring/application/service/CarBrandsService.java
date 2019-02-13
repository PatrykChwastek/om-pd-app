package com.spring.application.service;

import com.spring.application.model.CarBrand;
import com.spring.application.model.User;
import com.spring.application.repo.CarBrandRepo;
import com.spring.application.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CarBrandsService {
    @Autowired
    private CarBrandRepo carBrandRepo;


    public List<CarBrand> getAllBrands() {
        return carBrandRepo.findAll();
    }

}
