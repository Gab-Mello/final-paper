package com.gabriel.pive.fiv.oocyteCollection.repositories;

import com.gabriel.pive.fiv.oocyteCollection.entities.OocyteCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OocyteCollectionRepository extends JpaRepository<OocyteCollection, Long> {
    List<OocyteCollection> findByBullRegistrationNumber(String registrationNumber);

    List<OocyteCollection> findByDonorCattleRegistrationNumber(String registrationNumber);
}