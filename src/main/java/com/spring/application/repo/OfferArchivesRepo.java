package com.spring.application.repo;

import com.spring.application.model.Offer;
import com.spring.application.model.OfferArchives;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferArchivesRepo extends JpaRepository<OfferArchives, Integer> {


		OfferArchives getOfferArchivesById(int id);

		Page<OfferArchives> findAllByOwner(String owner, Pageable pageable);

}