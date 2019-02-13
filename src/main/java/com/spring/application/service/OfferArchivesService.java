package com.spring.application.service;

import com.spring.application.model.Offer;
import com.spring.application.model.OfferArchives;
import com.spring.application.repo.OfferArchivesRepo;
import com.spring.application.repo.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OfferArchivesService {
    @Autowired
    private OfferArchivesRepo offerArchivesRepo;

    public void offerToArchives(Offer offer){
        OfferArchives offerArchives = new OfferArchives();

        offerArchives.setId(offer.getId());
        offerArchives.setOwner(offer.getOwner());
        offerArchives.setContent(offer.getContent());
        offerArchives.setDate(offer.getDate());
        offerArchives.setPhoto1(offer.getPhoto1());
        offerArchives.setPhoto2(offer.getPhoto2());
        offerArchives.setPhoto3(offer.getPhoto3());
        offerArchives.setPhoto4(offer.getPhoto4());
        offerArchives.setPhoto5(offer.getPhoto5());
        offerArchives.setTitle(offer.getTitle());
        offerArchives.setBrand(offer.getBrand());
        offerArchives.setModel(offer.getModel());
        offerArchives.setType(offer.getType());
        offerArchives.setProduction(offer.getProduction());
        offerArchives.setMileage(offer.getMileage());
        offerArchives.setCapacity(offer.getCapacity());
        offerArchives.setPower(offer.getPower());
        offerArchives.setFuel(offer.getFuel());
        offerArchives.setPrice(offer.getPrice());
        offerArchives.setGearbox(offer.getGearbox());

        offerArchivesRepo.save(offerArchives);
    }

    public Page<OfferArchives> findPaginated(int page, int size, Sort sort) {
        return offerArchivesRepo.findAll(new PageRequest(page, size,sort));
    }
    public Page<OfferArchives> findUserArchive(int page, int size, String owner,Sort sort) {
        return offerArchivesRepo.findAllByOwner(owner,new PageRequest(page, size,sort));
    }

    public OfferArchives findOne(int id){
        return offerArchivesRepo.getOfferArchivesById(id);
    }
}
