package com.devsmeet.meetup

import org.springframework.lang.NonNull
import org.springframework.lang.Nullable

data class EventRequest(
        @NonNull
        var title: String,
        @NonNull var description: String,
        @NonNull var instructorId: Long,
        @Nullable var image: String
)

data class ParticipantRequest(
        @NonNull var name: String
)

data class JoinToEventRequest(
        @NonNull var participantId: Long
)

data class InstructorRequest(
        @NonNull var name: String,
        @NonNull var profession: String
)