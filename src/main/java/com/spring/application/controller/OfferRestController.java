package com.spring.application.controller;

import com.spring.application.model.Offer;
import com.spring.application.repo.OfferRepo;
import com.spring.application.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class OfferRestController {
    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private OfferService offerService;

    private static String photosPath = "src/main/resources/static/photos/";

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    public List<Offer> allOffers() {
        return offerRepo.findAll();
    }

    @RequestMapping(
            value = "/offers/get",
            params = {"page", "size"},
            method = RequestMethod.GET
    )
    public Page<Offer> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size) {

            Page<Offer> resultPage = offerService.findPaginated(page, size, Sort.by("date").descending());
        return resultPage;
    }

    @RequestMapping(value = "/offer/{id}", method = RequestMethod.GET)
    public Offer getOfferById(@PathVariable("id") int id) {

        return offerRepo.getOfferById(id);
    }
    @RequestMapping(value = "/offer/{id}/{owner}", method = RequestMethod.GET)
    public Offer getUserOfferById(@PathVariable("id") int id,@PathVariable("owner") String owner) {

        return offerRepo.getOfferByIdAndOwner(id,owner);
    }

    @RequestMapping(
            value = "/offers/get",
            params = {"page", "size","title"},
            method = RequestMethod.GET
    )
    public Page<Offer> findOffers(
            @RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("title") String title) {

        Page<Offer> resultPage = offerService.findOffer(page, size,title,Sort.by("date").descending());
        return resultPage;
    }

    @RequestMapping(
            value = "/user/offers",
            params = { "page", "size","owner" },
            method = RequestMethod.GET
    )
    public Page<Offer> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("owner") String owner) {

        Page<Offer> resultPage = offerService.findAllUserOffers(page, size,owner, Sort.by("date").descending() );
        return resultPage;
    }

    @RequestMapping(
            value = "/user/offers",
            params = { "page", "size","owner","title" },
            method = RequestMethod.GET
    )
    public Page<Offer> findUserOffers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("owner") String owner,
            @RequestParam("title") String title) {

        Page<Offer> resultPage = offerService.findUserOffers(page, size,title,owner, Sort.by("date").descending() );
        return resultPage;
    }

    @RequestMapping(value = "/filter/{title}/{brand}/{model}/{type}/{fuel}/{page}/{size}", method = RequestMethod.GET)
    public Page<Offer> filterOffers (@PathVariable(value = "brand", required = false) String brand,
                                     @PathVariable(value = "model", required = false) String model,
                                     @PathVariable(value = "title", required = false) String title,
                                     @PathVariable(value = "type", required = false) String type,
                                     @PathVariable(value = "fuel", required = false) String fuel,
                                     @PathVariable(value = "page") int page,
                                     @PathVariable(value = "size") int size
                                     ) {
        return offerService.filtering(page,size,'%'+title+'%',brand,model,type,fuel);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/newoffer", method = RequestMethod.POST)
    public void createOffer(@RequestParam("content") String content,
                            @RequestParam("owner") String owner,
                            @RequestParam("photo1") MultipartFile photo1,
                            @RequestParam(value = "photo2", required = false) MultipartFile photo2,
                            @RequestParam(value = "photo3", required = false) MultipartFile photo3,
                            @RequestParam(value = "photo4", required = false) MultipartFile photo4,
                            @RequestParam(value = "photo5", required = false) MultipartFile photo5,
                            @RequestParam("title") String title,
                            @RequestParam("brand") String brand,
                            @RequestParam("model") String model,
                            @RequestParam("type") String type,
                            @RequestParam("production") int production,
                            @RequestParam("mileage") int mileage,
                            @RequestParam("capacity") int capacity,
                            @RequestParam("power") int power,
                            @RequestParam("fuel") String fuel,
                            @RequestParam("gearbox") String gearbox,
                            @RequestParam("price") int price
                            ) {
        Offer offer = new Offer();
        int newId = offerService.findLast() + 1;


        offer.setId(newId);
        offer.setOwner(owner);
        offer.setContent(content);

        offer.setPhoto1("/photos/" +
                uploadPhoto(photo1, newId, "z1"));

        if (photo2 != null) {
            offer.setPhoto2("/photos/" +
                    uploadPhoto(photo2, newId, "z2"));
        } else {
            offer.setPhoto2("brak");
        }

        if (photo3 != null) {
            offer.setPhoto3("/photos/" +
                    uploadPhoto(photo3, newId, "z3"));
        } else {
            offer.setPhoto3("brak");
        }

        if (photo4 != null) {
            offer.setPhoto4("/photos/" +
                    uploadPhoto(photo4, newId, "z4"));
        } else {
            offer.setPhoto4("brak");
        }

        if (photo5 != null) {
            offer.setPhoto5("/photos/" +
                    uploadPhoto(photo5, newId, "z5"));
        } else {
            offer.setPhoto5("brak");
        }

        offer.setTitle(title);
        offer.setBrand(brand);
        offer.setModel(model);
        offer.setType(type);
        offer.setProduction(production);
        offer.setMileage(mileage);
        offer.setCapacity(capacity);
        offer.setPower(power);
        offer.setFuel(fuel);
        offer.setPrice(price);
        offer.setGearbox(gearbox);

        offerRepo.save(offer);
    }


    public String uploadPhoto(MultipartFile fileToUpload, int offerId, String photoNum) {
        String newName = "";
        try {
            byte[] bytes = fileToUpload.getBytes();
            newName = offerId + photoNum +
                    fileToUpload.getOriginalFilename().
                            substring(fileToUpload.getOriginalFilename().length() - 4);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(
                            new File(photosPath + newName)));
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
        } catch (IOException ex) {
            System.out.println("=======() Upload Error");
        }
        return newName;
    }
}
