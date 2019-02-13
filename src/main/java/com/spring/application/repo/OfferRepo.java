package com.spring.application.repo;

import com.spring.application.model.Offer;
import com.spring.application.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, Integer> {


		Offer getOfferById(int id);
		Offer getOfferByIdAndOwner(int id,String owner);

		Page<Offer> findAllByOwner(String owner,Pageable pageable);
		List<Offer> findAllByOwner(String owner);

	Page<Offer>findAllByTitleLikeIgnoreCase(String title, Pageable pageable);
	Page<Offer>findAllByTitleLikeIgnoreCaseAndOwner(String title,String owner, Pageable pageable);

	Page<Offer>findAllByTitleLikeIgnoreCaseAndBrand(String title,String brand, Pageable pageable);
	Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndModel(String title,String brand,String model, Pageable pageable);
	Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndModelAndType(String title,String brand,String model,String type, Pageable pageable);
	Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndModelAndTypeAndFuel(String title,String brand,String model,String type,String fuel, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndType(String title,String brand,String type, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndTypeAndFuel(String title,String brand,String type,String fuel, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndModelAndFuel(String title,String brand,String model,String fuel, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndBrandAndFuel(String title,String brand,String fuel, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndTypeAndFuel(String title,String type,String fuel, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndType(String title,String type, Pageable pageable);
    Page<Offer>findAllByTitleLikeIgnoreCaseAndFuel(String title,String fuel, Pageable pageable);

    Page<Offer>findAllByBrandAndModelAndType(String brand, String model, String type, Pageable pageable);
	Page<Offer>findAllByBrandAndModel(String brand, String model, Pageable pageable);
	Page<Offer>findAllByBrand(String brand, Pageable pageable);
	Page<Offer>findAllByBrandAndType(String brand, String type, Pageable pageable);

	Page<Offer>findAllByBrandAndModelAndTypeAndFuel(String brand, String model, String type, String fuel, Pageable pageable);
	Page<Offer>findAllByBrandAndModelAndFuel(String brand, String model, String fuel, Pageable pageable);
	Page<Offer>findAllByBrandAndFuel(String brand, String fuel, Pageable pageable);
	Page<Offer>findAllByBrandAndTypeAndFuel(String brand, String type,String fuel, Pageable pageable);
	Page<Offer>findAllByTypeAndFuel(String type,String fuel, Pageable pageable);

	Page<Offer>findAllByType(String type, Pageable pageable);
	Page<Offer>findAllByFuel(String fuel, Pageable pageable);

}