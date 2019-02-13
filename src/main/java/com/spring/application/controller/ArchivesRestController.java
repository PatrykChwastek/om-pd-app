package com.spring.application.controller;

import com.spring.application.model.Offer;
import com.spring.application.model.OfferArchives;
import com.spring.application.repo.OfferArchivesRepo;
import com.spring.application.repo.OfferRepo;
import com.spring.application.service.OfferArchivesService;
import com.spring.application.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class ArchivesRestController {
    @Autowired
    private OfferRepo offerRepo;


    @Autowired
    private OfferArchivesService offerArchivesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(
            value = "/archives/get",
            params = {"page", "size"},
            method = RequestMethod.GET
    )
    public Page<OfferArchives> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size) {

            Page<OfferArchives> resultPage = offerArchivesService.findPaginated(page, size, Sort.by("archiveDate").descending());
        return resultPage;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/offer/archives/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Offer> offerToArchives(@PathVariable int id) {
        Offer offer = offerRepo.getOfferById(id);

        if (offer == null) {
            return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
        } else {
            offerArchivesService.offerToArchives(offer);
            offerRepo.delete(offer);
            return new ResponseEntity<Offer>(offer, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/archives/{id}", method = RequestMethod.GET)
    public OfferArchives getOfferArchiveById(@PathVariable("id") int id) {

        return offerArchivesService.findOne(id);
    }

    @RequestMapping(
            value = "/user/archives",
            params = { "page", "size","owner" },
            method = RequestMethod.GET
    )
    public Page<OfferArchives> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("owner") String owner) {

        Page<OfferArchives> resultPage = offerArchivesService.findUserArchive(page, size,owner,Sort.by("archiveDate").descending() );
        if (page > resultPage.getTotalPages()) {
            page=resultPage.getTotalPages()-1;
        }

        return resultPage;
    }
}
