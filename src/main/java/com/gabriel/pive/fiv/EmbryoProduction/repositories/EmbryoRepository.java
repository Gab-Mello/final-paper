package com.gabriel.pive.fiv.EmbryoProduction.repositories;

import com.gabriel.pive.animals.entities.Bull;
import com.gabriel.pive.animals.entities.DonorCattle;
import com.gabriel.pive.animals.entities.ReceiverCattle;
import com.gabriel.pive.fiv.EmbryoProduction.entities.Embryo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbryoRepository extends JpaRepository<Embryo,Long> {

    List<Embryo> findAllByEmbryoBull(Bull bull);

    List<Embryo> findAllByEmbryoDonorCattle(DonorCattle donorCattle);

    Embryo findByEmbryoReceiverCattle(ReceiverCattle receiverCattle);
}