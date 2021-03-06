package com.analuciabolico.assembly.v1.api.schedule;

import com.analuciabolico.assembly.v1.core.model.ResourceCreated;
import com.analuciabolico.assembly.v1.schedule.dto.ScheduleDto;
import com.analuciabolico.assembly.v1.schedule.dto.ScheduleResultDto;
import com.analuciabolico.assembly.v1.schedule.dto.ScheduleSessionDto;
import com.analuciabolico.assembly.v1.schedule.enums.ScheduleResultEnum;
import com.analuciabolico.assembly.v1.schedule.model.Schedule;
import com.analuciabolico.assembly.v1.schedule.service.interfaces.IScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final IScheduleService scheduleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Schedule>> getAll(@RequestParam(required = false, defaultValue = "ASC") String sort) {
        List<Schedule> list = scheduleService.findAll(Sort.by(Sort.Direction.fromString(sort), "title"));
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> findById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findById(id), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceCreated> save(@RequestBody ScheduleDto schedule) {
        return new ResponseEntity<>(scheduleService.save(schedule), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/session", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> session(@PathVariable Long id, @RequestBody(required = false) ScheduleSessionDto schedule) {
        return new ResponseEntity<>(scheduleService.session(schedule, id), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/result", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleResultEnum> result(@PathVariable Long id, @RequestBody ScheduleResultDto status) {
        return new ResponseEntity<>(scheduleService.result(status, id), HttpStatus.OK);
    }

}