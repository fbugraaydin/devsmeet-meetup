package com.devsmeet.meetup.controller

import com.devsmeet.meetup.EventDto
import com.devsmeet.meetup.EventRequest
import com.devsmeet.meetup.services.EventService
import com.devsmeet.meetup.services.InstructorService
import com.devsmeet.meetup.services.ParticipantService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService,
                      private val participantService: ParticipantService,
                      private val instructorService: InstructorService) {

    @GetMapping
    fun listEvents(): List<EventDto> = eventService.getEventList()

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: Long) = eventService.getEventById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createEvent(@Validated @RequestBody req: EventRequest){
        val instructorDto = instructorService.getInstructorById(req.instructorId)
        val event = EventDto(title = req.title, description = req.description,
                instructor = instructorDto, date = LocalDate.now().plusDays(4))
        eventService.createEvent(event)
    }

    @PutMapping("/{id}")
    fun updateEvent(@Validated @RequestBody req: EventRequest, @PathVariable id: Long) {
        val instructorDto = instructorService.getInstructorById(req.instructorId)
        val event = EventDto(title = req.title, description = req.description,
                instructor = instructorDto, date = LocalDate.now().plusDays(4))
        eventService.updateEvent(event)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteEventById(@PathVariable id: Long) = eventService.deleteEventById(id)

    @GetMapping("/{id}/participants")
    fun getParticipantsOfEvent(@PathVariable id: Long) = participantService.findParticipantsById(id)


}