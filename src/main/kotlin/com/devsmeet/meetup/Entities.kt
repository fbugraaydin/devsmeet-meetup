package com.devsmeet.meetup

import java.time.LocalDate
import javax.persistence.*


@Entity
@Table(name = "event")
class Event(@Id @GeneratedValue var id: Long? = null,

            var title: String,

            @Lob var description: String,

            @OneToOne
            @JoinColumn(name = "instructor_id", referencedColumnName = "id")
            var instructor: Instructor,

            @Lob var image: String?,

            var date: LocalDate,

            @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "event_participant", joinColumns = [JoinColumn(name = "event_id", referencedColumnName = "id")],
                    inverseJoinColumns = [JoinColumn(name = "participant_id", referencedColumnName = "id")])
            var participants: MutableList<Participant>? = null
)

@Entity
@Table(name = "instructor")
class Instructor(
        @Id @GeneratedValue
        var id: Long? = null,
        var name: String,
        var profession: String
)

@Entity
@Table(name = "participant")
class Participant(
        @Id @GeneratedValue
        var id: Long? = null,
        var name: String,
        @ManyToMany(mappedBy = "participants")
        var events: MutableList<Event>? = null
)

