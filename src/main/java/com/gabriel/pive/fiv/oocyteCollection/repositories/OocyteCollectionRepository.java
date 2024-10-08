package com.gabriel.pive.fiv.oocyteCollection.repositories;

import com.gabriel.pive.animals.entities.Bull;
import com.gabriel.pive.animals.entities.DonorCattle;
import com.gabriel.pive.fiv.oocyteCollection.entities.OocyteCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OocyteCollectionRepository extends JpaRepository<OocyteCollection, Long> {
    List<OocyteCollection> findByBullRegistrationNumber(String registrationNumber);

    List<OocyteCollection> findByDonorCattleRegistrationNumber(String registrationNumber);

    List<OocyteCollection> findAllByBull(Bull bull);

    List<OocyteCollection> findAllByDonorCattle(DonorCattle donorCattle);

    @Query("SELECT oc FROM OocyteCollection oc " +
            "WHERE oc.donorCattle = :donor AND oc.bull = :bull")
    List<OocyteCollection> findByDonorAndBull(DonorCattle donor, Bull bull);
}
