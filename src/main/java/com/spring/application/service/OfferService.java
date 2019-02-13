package com.spring.application.service;

import com.spring.application.model.Offer;
import com.spring.application.repo.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OfferService {
    @Autowired
    private OfferRepo offerRepo;

    public List<Offer> getAllOffers() {
        return offerRepo.findAll();
    }

    public int findLast() {
        List<Offer> allOffers = getAllOffers();

        Offer maxId = allOffers.
                stream().
                max(Comparator.comparing(Offer::getId)).
                orElseThrow(NoSuchElementException::new);

        return maxId.getId();

    }


    public Page<Offer> findPaginated(int page, int size, Sort sort) {
        return offerRepo.findAll(new PageRequest(page, size,sort));
    }
    public Page<Offer> findOffer(int page, int size,String title, Sort sort) {
        return offerRepo.findAllByTitleLikeIgnoreCase('%'+title+'%',new  PageRequest(page, size,sort));
    }

    public Page<Offer> findUserOffers(int page, int size,String title,String owner, Sort sort) {
        return offerRepo.findAllByTitleLikeIgnoreCaseAndOwner('%'+title+'%',owner,new PageRequest(page, size,sort));
    }

    public Page<Offer> findAllUserOffers(int page, int size, String owner,Sort sort) {
        return offerRepo.findAllByOwner(owner, new PageRequest(page, size,sort));
    }

    public List<Offer> findUserOfferList(String userName){
        return offerRepo.findAllByOwner(userName);
    }

    public Page<Offer> filtering(int page, int size,
                                 String title,
                                 String brand,
                                 String model,
                                 String type,
                                 String fuel) {

        if (!title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCase(title, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrand(title,brand, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndModel(title,brand,model, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && !type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndModelAndType(title,brand,model,type, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && !type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndModelAndTypeAndFuel(title,brand,model,type, fuel, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndType(title,brand,type, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndTypeAndFuel(title,brand,type,fuel, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndModelAndFuel(title,brand,model,fuel, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndBrandAndFuel(title,brand,fuel, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndTypeAndFuel(title,type,fuel, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndType(title,type, new PageRequest(page, size));
        } else if (!title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTitleLikeIgnoreCaseAndFuel(title,fuel, new PageRequest(page, size));
        } else

        if (title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && !type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndModelAndType(brand, model, type, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndModel(brand, model, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByBrand(brand, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndType(brand, type, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && !type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndModelAndTypeAndFuel(brand, model, type, fuel, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && !brand.equals("undefined") && !model.equals("undefined") && type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndModelAndFuel(brand, model, fuel, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndFuel(brand, fuel, new PageRequest(page, size));
        }else if (title.equals("%undefined%") && !brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByBrandAndTypeAndFuel(brand,type, fuel, new PageRequest(page, size));
        }else if (title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && fuel.equals("undefined")) {
            return offerRepo.findAllByType(type, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByFuel(fuel, new PageRequest(page, size));
        } else if (title.equals("%undefined%") && brand.equals("undefined") && model.equals("undefined") && !type.equals("undefined") && !fuel.equals("undefined")) {
            return offerRepo.findAllByTypeAndFuel(type, fuel, new PageRequest(page, size));
        }else  return findPaginated(page, size, Sort.by("date").descending());
    }
}
