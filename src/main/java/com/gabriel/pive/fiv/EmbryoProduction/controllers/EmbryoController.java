package com.gabriel.pive.fiv.EmbryoProduction.controllers;

import com.gabriel.pive.fiv.EmbryoProduction.dtos.*;
import com.gabriel.pive.fiv.EmbryoProduction.services.EmbryosService;
import com.gabriel.pive.fiv.EmbryoProduction.services.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Embryos", description = "Embryos management")
@RestController
@RequestMapping("/embryo")
public class EmbryoController {

    @Autowired
    private EmbryosService embryosService;

    @Autowired
    private TransferService transferService;

    @Operation(summary = "Save a embryo in a transfer", description = "It saves and returns a json with the transfer")
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDto> saveEmbryoInTransfer(@Valid @RequestBody TransferDataDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.saveTransferData(dto));
    }

    @Operation(summary = "Save frozen embryos", description = "It saves and returns a json with the production")
    @PostMapping("/frozen")
    public ResponseEntity<ProductionResponseDto> saveFrozenEmbryos(@Valid @RequestBody FrozenEmbryosDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(embryosService.frozenEmbryos(dto));
    }

    @Operation(summary = "Save number of discarded embryos", description = "It saves the number of discarded fivs")
    @PostMapping("/discarded")
    public ResponseEntity<?> saveFrozenEmbryos(@Valid @RequestBody DiscardedEmbryosDto dto){
        embryosService.discardedEmbryos(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Get all embryos", description = "It returns a json list with all embryos")
    @GetMapping
    public ResponseEntity<List<EmbryoResponseDto>> getAllEmbryos(){
        return ResponseEntity.status(HttpStatus.OK).body(embryosService.getAllEmbryos());
    }

    @Operation(summary = "Get a embryo by Id", description = "It returns a json with the embryo")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmbryoById(@PathVariable Long id){
            return ResponseEntity.status(HttpStatus.OK).body(embryosService.getEmbryoById(id));
    }

    }
