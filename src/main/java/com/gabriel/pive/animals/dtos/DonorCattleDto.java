package com.gabriel.pive.animals.dtos;

import com.gabriel.pive.animals.entities.DonorCattle;

import java.time.LocalDate;
import java.util.List;

public record DonorCattleDto(String name, String breed, LocalDate birth, Integer registrationNumber) {

    public DonorCattle toDonorCattle(){
        return new DonorCattle(
                name,
                breed,
                birth,
                registrationNumber);
    }

    public static DonorCattleDto toDonorCattleDto(DonorCattle dto){
        return new DonorCattleDto(
                dto.getName(),
                dto.getBreed(),
                dto.getBirth(),
                dto.getRegistrationNumber()
        );
    }
    public static List<DonorCattleDto> toDonorCattleDtoList(List<DonorCattle> list){
        List<DonorCattleDto> listDto = list.stream().
                map(donorCattle -> DonorCattleDto.toDonorCattleDto(donorCattle)).
                toList();

        return listDto;
    }

}