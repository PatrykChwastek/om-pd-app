package com.spring.application.controller;

import com.spring.application.model.CarBrand;
import com.spring.application.model.User;
import com.spring.application.repo.CarBrandRepo;
import com.spring.application.repo.UserRepo;
import com.spring.application.service.CarBrandsService;
import com.spring.application.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
public class HomeRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CarBrandRepo carBrandRepo;

    @RequestMapping(value = "/brands", method = RequestMethod.GET)
    public List<CarBrand> allBrands() {
        return carBrandRepo.findAll();
    }

    @RequestMapping(value = "/brands/save/{name}", method = RequestMethod.GET)
    public CarBrand saveBrand(@PathVariable("name") String name) {
        CarBrand carBrand= new CarBrand();
        carBrand.setName(name);
        return carBrandRepo.save(carBrand);
    }

    @RequestMapping(value = "/brands/save/model/{brand}/{model}", method = RequestMethod.GET)
    public CarBrand saveModel(@PathVariable("brand") String brandName,@PathVariable("model") String model) {
        CarBrand carBrand= carBrandRepo.findByName(brandName);
        List<String> models = carBrand.getModel();
        models.add(model);
        carBrand.setModel(models);
        return carBrandRepo.save(carBrand);
    }

    @RequestMapping(value = "/brands/del/{name}", method = RequestMethod.GET)
    public void saveDelete(@PathVariable("name") String name) {
        CarBrand carBrand= carBrandRepo.findByName(name);
        carBrandRepo.delete(carBrand);
    }

    @RequestMapping(value = "/brands/del/model/{brand}/{model}", method = RequestMethod.GET)
    public CarBrand deleteModel(@PathVariable("brand") String brandName,@PathVariable("model") String model) {
        CarBrand carBrand= carBrandRepo.findByName(brandName);
        List<String> models = carBrand.getModel();

        for (int i = 0; i <models.size(); i++) {
            if (models.get(i).equals(model)){
                models.remove(i);
            }
        }
        carBrand.setModel(models);
        return carBrandRepo.save(carBrand);
    }

    @RequestMapping(value = "/brands/{name}", method = RequestMethod.GET)
    public CarBrand getBrandByName(@PathVariable("name") String name) {

        return carBrandRepo.findByName(name);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User appUser) {
        if (userRepo.findOneByUsername(appUser.getUsername()) != null) {
            throw new RuntimeException("Username already exist");
        }
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        appUser.setRoles(roles);
        return new ResponseEntity<User>(userRepo.save(appUser), HttpStatus.CREATED);
    }

    @RequestMapping("/user")
    public User user(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return userRepo.findOneByUsername(loggedUsername);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
                                                     HttpServletResponse response) throws IOException {
        String token = null;
        User user = userRepo.findOneByUsername(username);
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        if (user != null && user.getPassword().equals(password)) {
            token = Jwts.builder().setSubject(username).claim("roles", user.getRoles()).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            tokenMap.put("token", token);
            tokenMap.put("user", user);
            return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
        } else {
            tokenMap.put("token", null);
            return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
        }

    }
}
