package com.gabriel.pive.animals.services;

import com.gabriel.pive.animals.dtos.ReceiverCattleDto;
import com.gabriel.pive.animals.exceptions.ReceiverCattleNotFoundException;
import com.gabriel.pive.animals.exceptions.RegistrationNumberAlreadyExistsException;
import com.gabriel.pive.animals.repositories.ReceiverCattleRepository;
import com.gabriel.pive.animals.entities.ReceiverCattle;
import com.gabriel.pive.fiv.EmbryoProduction.entities.Embryo;
import com.gabriel.pive.fiv.EmbryoProduction.repositories.EmbryoRepository;
import com.gabriel.pive.fiv.pregnancy.enums.PregnancyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiverCattleService {

    @Autowired
    private ReceiverCattleRepository receiverCattleRepository;

    @Autowired
    private EmbryoRepository embryoRepository;

    public ReceiverCattleDto create(ReceiverCattleDto dto){
        if (receiverCattleRepository.findByRegistrationNumber(dto.registrationNumber()) != null){
            throw new RegistrationNumberAlreadyExistsException("Uma receptora com este número de registro já existe.");
        }
        ReceiverCattle receiverCattle = receiverCattleRepository.save(dto.toReceiverCattle());
        return ReceiverCattleDto.toReceiverCattleDto(receiverCattle);
    }

    public List<ReceiverCattleDto> findAll(){
        List<ReceiverCattle> list = receiverCattleRepository.findAll();
        return ReceiverCattleDto.toReceiverCattleDtoList(list);
    }

    public List<ReceiverCattleDto> findByRegistrationNumber(String registrationNumber){
        return ReceiverCattleDto.toReceiverCattleDtoList(receiverCattleRepository.
                findByRegistrationNumberStartingWith(registrationNumber.toUpperCase()));
    }

    public ReceiverCattleDto findById(Long id){
        ReceiverCattle receiverCattle = receiverCattleRepository.findById(id)
                .orElseThrow(ReceiverCattleNotFoundException::new);
        return ReceiverCattleDto.toReceiverCattleDto(receiverCattle);
    }

    public void delete(Long id){
        ReceiverCattle receiverCattle = receiverCattleRepository.findById(id)
                .orElseThrow(ReceiverCattleNotFoundException::new);

        Embryo embryo = embryoRepository.findByEmbryoReceiverCattle(receiverCattle);
        embryo.setEmbryoReceiverCattle(null);
        embryoRepository.save(embryo);

        receiverCattleRepository.deleteById(id);
    }

    public ReceiverCattleDto edit(Long id, ReceiverCattleDto dto){
        ReceiverCattle receiverCattle = receiverCattleRepository.findById(id)
                .orElseThrow(ReceiverCattleNotFoundException::new);

        if (receiverCattleRepository.findByRegistrationNumber(dto.registrationNumber()) != receiverCattle
            && receiverCattleRepository.findByRegistrationNumber(dto.registrationNumber()) != null){
            throw new RegistrationNumberAlreadyExistsException("Uma receptora com este número de registro já existe.");
        }

        receiverCattle.setName(dto.name());
        receiverCattle.setBreed(dto.breed());
        receiverCattle.setRegistrationNumber(dto.registrationNumber());

        return ReceiverCattleDto.toReceiverCattleDto(receiverCattleRepository.save(receiverCattle));
    }

    public List<ReceiverCattleDto> getAvailableReceivers(){
        return ReceiverCattleDto.toReceiverCattleDtoList(receiverCattleRepository.findByEmbryoIsNull());
    }

    public List<ReceiverCattleDto> getAllPregnantReceivers(){
        return ReceiverCattleDto.toReceiverCattleDtoList(receiverCattleRepository.findByPregnancyStatus(PregnancyStatus.PREGNANT));
    }

    public List<ReceiverCattleDto> getAllInProgressPregnantReceivers(Long fivId){
        return ReceiverCattleDto.toReceiverCattleDtoList(receiverCattleRepository.findAllInProgressByFivId(fivId));
    }
}
