package com.devsmeet.meetup

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(uses = [InstructorMapper::class, ParticipantMapper::class], componentModel = "spring")
interface EventMapper {

    fun convertToDto(event: Event): EventDto

    @InheritInverseConfiguration
    fun convertToEntity(eventDto: EventDto): Event
}

@Mapper(componentModel="spring")
interface InstructorMapper{

    fun convertToDto(instructor: Instructor): InstructorDto

    fun convertToEntity(instructorDto: InstructorDto): Instructor

}

@Mapper(uses = [EventMapper::class], componentModel = "spring")
interface ParticipantMapper {

    fun convertToDto(participant: Participant): ParticipantDto

    fun convertToEntity(eventDto: ParticipantDto): Participant

}