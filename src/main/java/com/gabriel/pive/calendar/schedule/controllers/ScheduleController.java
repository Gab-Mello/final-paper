package com.gabriel.pive.calendar.schedule.controllers;

import com.gabriel.pive.calendar.schedule.dtos.ScheduleRequestDto;
import com.gabriel.pive.calendar.schedule.dtos.ScheduleResponseDto;
import com.gabriel.pive.calendar.schedule.services.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("schedule")
@Tag(name = "Schedule", description = "Calendar management")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Operation(summary = "Save a new schedule", description = "It saves and returns a json with the new schedule")
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(dto));
    }

    @Operation(summary = "Edit a schedule by id", description = "It edits the schedule's data")
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> editSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.editSchedule(id, dto));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Operation(summary = "Cancel a schedule by Id", description = "It cancels the schedule ")
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> cancelSchedule(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.cancelSchedule(id));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();

        }
    }

    @Operation(summary = "Filter the schedules by date", description = "It returns a list of all schedules with the specific date")
    @GetMapping("/search")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByDate(@Parameter(description = "Example: http://localhost:8080/schedule/search?date=2024-08-01")
                                                                            @RequestParam LocalDate date){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedulesByDate(date));
    }

    @Operation(summary = "Get all the schedules", description = "It returns a list with all pending schedules")
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> listAllSchedules(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.listAllSchedules());
    }

}
