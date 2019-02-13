package com.spring.application.controller;

import com.spring.application.model.Offer;
import com.spring.application.model.OfferArchives;
import com.spring.application.model.User;
import com.spring.application.repo.OfferRepo;
import com.spring.application.repo.UserRepo;
import com.spring.application.service.OfferArchivesService;
import com.spring.application.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private OfferArchivesService offerArchivesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> users() {
        return userRepo.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> userById(@PathVariable int id) {
        User appUser = userRepo.getUserById(id);
        if (appUser == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<User>(appUser, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        User appUser = userRepo.getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        if (appUser == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        } else if (appUser.getUsername().equalsIgnoreCase(loggedUsername)) {
            throw new RuntimeException("Ne można usunąć aktualnie zalogowanego administratora");
        } else {
            userRepo.delete(appUser);
            return new ResponseEntity<User>(appUser, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/users/del/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserAcc(@PathVariable int id) {
        User appUser = userRepo.getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        if (appUser == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        } else if (appUser.getUsername().equalsIgnoreCase(loggedUsername)) {
            for (Offer userOffer: offerService.findUserOfferList(loggedUsername)
                 ) {
                        offerArchivesService.offerToArchives(userOffer);
                        offerRepo.delete(userOffer);
            }
            userRepo.delete(appUser);
            return new ResponseEntity<User>(appUser, HttpStatus.OK);
        } else {
            throw new RuntimeException("Ne można usunąć tego konta");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User appUser) {
        if (userRepo.findOneByUsername(appUser.getUsername()) != null) {
            throw new RuntimeException("Nazwa użytkownika jest zajęta");
        }
        return new ResponseEntity<User>(userRepo.save(appUser), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User appUser) {
        if (userRepo.findOneByUsername(appUser.getUsername()) != null
                && userRepo.findOneByUsername(appUser.getUsername()).getId() != appUser.getId()) {
            throw new RuntimeException("Nazwa użytkownika jest zajęta");
        }
        return userRepo.save(appUser);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        return userRepo.findOneByUsername(username);
    }
}
