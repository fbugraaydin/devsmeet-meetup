package com.devsmeet.meetup

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: JpaRepository<Event,Long>{
    @Query(value = "SELECT t FROM Event t join t.participants p WHERE p.id = :participantId")
    fun findEventsByParticipantId(participantId: Long): List<Event>
}

@Repository
interface InstructorRepository : JpaRepository<Instructor, Long>

@Repository
interface ParticipantRepository : CrudRepository<Participant, Long> {
    @Query(value = "SELECT t FROM Participant t join t.events p WHERE p.id = :eventId")
    fun findParticipantsByEventId(eventId: Long): List<Participant>
}