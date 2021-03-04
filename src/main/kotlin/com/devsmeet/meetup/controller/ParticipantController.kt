package com.devsmeet.meetup.controller

import com.devsmeet.meetup.JoinToEventRequest
import com.devsmeet.meetup.ParticipantDto
import com.devsmeet.meetup.ParticipantRequest
import com.devsmeet.meetup.services.EventService
import com.devsmeet.meetup.services.ParticipantService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/participants")
class ParticipantController(private val participantService: ParticipantService,
                            private val eventService: EventService) {

    @GetMapping
    fun listParticipants(): List<ParticipantDto> = participantService.getParticipantList()

    @GetMapping("/{id}")
    fun getParticipantById(@PathVariable id: Long) = participantService.getParticipantById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createParticipant(@Valid @RequestBody req: ParticipantRequest) {
        participantService.createParticipant(ParticipantDto(name = req.name))
    }

    @PutMapping("/{id}")
    fun updateParticipant(@Valid @RequestBody req: ParticipantRequest, @PathVariable id: Long) {
        participantService.updateParticipant(ParticipantDto(id = id, name = req.name))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteParticipantById(@PathVariable id: Long) = participantService.deleteParticipantById(id)

    @PatchMapping("/{id}")
    fun joinToEvent(@Valid @RequestBody req: JoinToEventRequest, @PathVariable id: Long) {
        participantService.updateParticipantEvents(id, req.participantId)
    }

    @GetMapping("/{id}/events")
    fun getEventsOfParticipant(@PathVariable id: Long) = eventService.findEventsById(id)

}