package com.devsmeet.meetup.services
import com.devsmeet.meetup.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ParticipantService(private val participantRepository: ParticipantRepository,
                         private val eventRepository: EventRepository,
                         private val participantMapper: ParticipantMapper) {

    fun getParticipantList(): List<ParticipantDto> = participantRepository.findAll().map {
        participantMapper.convertToDto(it)
    }

    fun createParticipant(participantDto: ParticipantDto) = participantRepository.save(participantMapper.convertToEntity(participantDto))

    fun getParticipantById(id: Long): ParticipantDto {
        val participant = isParticipantExist(id)
        return participantMapper.convertToDto(participant)
    }

    fun updateParticipant(participantDto: ParticipantDto) {
        val participant = isParticipantExist(participantDto.id!!)
        participant.name = participantDto.name
        participantRepository.save(participant)
    }

    fun deleteParticipantById(id: Long) {
        isParticipantExist(id)
        participantRepository.deleteById(id)
    }

    fun updateParticipantEvents(eventId: Long, participantId: Long) {
        val event = isEventExist(eventId)
        val participant = isParticipantExist(participantId)
        if (participant.events.isNullOrEmpty()) {
            participant.events = mutableListOf(event)
        } else {
            participant.events!!.add(event)
        }
        participantRepository.save(participant)
    }

    fun findParticipantsById(id: Long): List<ParticipantDto> {
        isEventExist(id)
        return participantRepository.findParticipantsByEventId(id).map {
            participantMapper.convertToDto(it)
        }
    }

    private fun isParticipantExist(id: Long) = participantRepository.findById(id).orElseThrow {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, Exception_Participant_Not_Found)
    }

    private fun isEventExist(id: Long): Event = eventRepository.findById(id).orElseThrow {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, Exception_Event_Not_Found)
    }


}