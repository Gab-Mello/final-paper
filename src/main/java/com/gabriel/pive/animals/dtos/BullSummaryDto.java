package com.gabriel.pive.animals.dtos;

import com.gabriel.pive.animals.entities.Bull;

public record BullSummaryDto(Long id, String name, String registrationNumber) {

    public static BullDto toBullSummaryDto(Bull bull){
        return new BullDto(
                bull.getId(),
                bull.getName(),
                bull.getRegistrationNumber()
        );
    }
}