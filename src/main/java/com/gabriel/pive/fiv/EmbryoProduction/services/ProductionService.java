package com.gabriel.pive.fiv.EmbryoProduction.services;

import com.gabriel.pive.fiv.EmbryoProduction.dtos.ProductionRequestDto;
import com.gabriel.pive.fiv.EmbryoProduction.dtos.ProductionResponseDto;
import com.gabriel.pive.fiv.EmbryoProduction.entities.EmbryoProduction;
import com.gabriel.pive.fiv.EmbryoProduction.exceptions.OocyteCollectionAlreadyHasProduction;
import com.gabriel.pive.fiv.EmbryoProduction.exceptions.ProductionNotFoundException;
import com.gabriel.pive.fiv.EmbryoProduction.exceptions.MoreViableThanTotalEmbryosException;
import com.gabriel.pive.fiv.EmbryoProduction.repositories.ProductionRepository;
import com.gabriel.pive.fiv.oocyteCollection.entities.OocyteCollection;
import com.gabriel.pive.fiv.oocyteCollection.exceptions.OocyteCollectionNotFoundException;
import com.gabriel.pive.fiv.oocyteCollection.repositories.OocyteCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionService {

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private OocyteCollectionRepository oocyteCollectionRepository;

    public ProductionResponseDto newProduction(ProductionRequestDto dto){

        OocyteCollection oocyteCollection = oocyteCollectionRepository.findById(dto.oocyteCollectionId())
                .orElseThrow(OocyteCollectionNotFoundException::new);

        if(oocyteCollection.getEmbryoProduction() != null ){
            throw new OocyteCollectionAlreadyHasProduction();
        }
        if (dto.viableEmbryos() > dto.totalEmbryos()){
            throw new MoreViableThanTotalEmbryosException();
        }

        EmbryoProduction embryoProduction = dto.toProduction(oocyteCollection);

        EmbryoProduction savedEmbryoProduction = productionRepository.save(embryoProduction);

        oocyteCollection.setEmbryoProduction(savedEmbryoProduction);
        oocyteCollectionRepository.save(oocyteCollection);

        return ProductionResponseDto.toProductionResponseDto(savedEmbryoProduction);

    }

    public List<ProductionResponseDto> getAllCultivations(){
        return ProductionResponseDto.toCultivationDtoList(productionRepository.findAll());
    }

    public ProductionResponseDto getCultivationById(Long id){
        EmbryoProduction embryoProduction = productionRepository.findById(id)
                .orElseThrow(ProductionNotFoundException::new);

        return ProductionResponseDto.toProductionResponseDto(embryoProduction);
    }

//    public CultivationResponseDto editCultivation(Long id, ProductionRequestDto dto){
//        EmbryoProduction embryoProduction = productionRepository.findById(id)
//                .orElseThrow(ProductionNotFoundException::new);
//
//        Fiv newFiv = fivRepository.findById(dto.fivId())
//                .orElseThrow(FivNotFoundException::new);
//
//        if (newFiv.getEmbryoProduction() != null){
//            throw  new FivAlreadyHasCultivation();
//        }
//
//        if (newFiv.getOocyteCollections().isEmpty()){
//            throw new FivDoesNotHaveOocyteCollectionException();
//        }
//
//        Fiv oldFiv = fivRepository.findByEmbryoProductionId(id);
//        oldFiv.setEmbryoProduction(null);
//        fivRepository.save(oldFiv);
//
//
//        embryoProduction.setFiv(newFiv);
//        embryoProduction.setTotalEmbryos(dto.totalEmbryos());
//        embryoProduction.setViableEmbryos(dto.viableEmbryos());
//
//        EmbryoProduction embryoProductionSaved = productionRepository.save(embryoProduction);
//
//        newFiv.setEmbryoProduction(embryoProductionSaved);
//        fivRepository.save(newFiv);
//
//        return CultivationResponseDto.toCultivationResponseDto(embryoProductionSaved);
//
//    }
}