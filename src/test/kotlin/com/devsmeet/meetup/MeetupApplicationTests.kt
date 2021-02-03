package com.devsmeet.meetup

import com.devsmeet.meetup.services.ParticipantService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.server.ResponseStatusException
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
class MeetupApplicationTests {

	@MockBean
	private lateinit var participantRepository: ParticipantRepository

	@Autowired
	private lateinit var participantService: ParticipantService

	private final val participant = Participant(id = 2, name = "Participant")

	@Test
	fun `When mock participant repository Then expect return its default value`(){
		Mockito.`when`(participantRepository.findById(anyLong())).thenReturn(Optional.of(this.participant))
		val participant: Participant = participantRepository.findById(1).get()
		Assertions.assertEquals("Participant",participant.name)
	}

	@Test
	fun `When delete participant by id with nonexist record Then throws Participant not found exception`() {
		Mockito.`when`(participantRepository.findById(anyLong())).thenReturn(Optional.empty())

		val exception = Assertions.assertThrows(ResponseStatusException::class.java) {
			participantService.deleteParticipantById(2)
		}

		Assertions.assertEquals(Exception_Participant_Not_Found, exception.reason)
		Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.status)
	}

	@Test
	fun `When find participant by id Then return mock one`() {
		Mockito.`when`(participantRepository.findById(100)).thenReturn(Optional.of(this.participant))

		val dummyParticipant = participantService.getParticipantById(100)

		Assertions.assertEquals(this.participant.name, dummyParticipant.name)
		Assertions.assertEquals(this.participant.id, dummyParticipant.id)
	}

}
