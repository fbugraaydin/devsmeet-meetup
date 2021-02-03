package com.devsmeet.meetup

import java.time.LocalDate

data class EventDto(
        var id:Long ? = null,
        var title: String,
        var description: String,
        var instructor: InstructorDto? = null,
        var image: String? = null,
        var date: LocalDate)

data class InstructorDto(
        var id: Long? = null,
        var name: String,
        var profession: String
)

data class ParticipantDto(
        var id: Long? = null,
        var name: String)
