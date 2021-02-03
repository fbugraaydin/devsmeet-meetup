package com.devsmeet.meetup.services

import com.devsmeet.meetup.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class EventService(private val eventRepository: EventRepository,
                   private val participantRepository: ParticipantRepository,
                   private val eventMapper: EventMapper,
                   private val instructorMapper: InstructorMapper) {

    fun getEventList(): List<EventDto> = eventRepository.findAll().map {
        eventMapper.convertToDto(it)
    }

    fun createEvent(eventDto: EventDto) = eventRepository.save(eventMapper.convertToEntity(eventDto))

    fun getEventById(id: Long): EventDto {
        val event = isEventExist(id)
        return eventMapper.convertToDto(event)
    }

    fun updateEvent(eventDto: EventDto){
        val event = isEventExist(eventDto.id!!)

        val instructor = instructorMapper.convertToEntity(eventDto.instructor!!)

        event.title = eventDto.title
        event.description = eventDto.description
        event.instructor = instructor
        event.image = eventDto.image
        event.date = eventDto.date

        eventRepository.save(event)
    }

    fun deleteEventById(id: Long) {
        isEventExist(id)
        eventRepository.deleteById(id)
    }

    fun findEventsById(id: Long): List<EventDto> {
        isParticipantExist(id)
        return eventRepository.findEventsByParticipantId(id).map {
            eventMapper.convertToDto(it)
        }
    }

    private fun isEventExist(id: Long): Event = eventRepository.findById(id).orElseThrow {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, Exception_Event_Not_Found)
    }

    private fun isParticipantExist(id: Long) = participantRepository.findById(id).orElseThrow {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, Exception_Participant_Not_Found)
    }

}