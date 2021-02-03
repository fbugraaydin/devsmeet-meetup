package com.devsmeet.meetup

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate

@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
class MeetupApplication{

	@Autowired
	private lateinit var eventRepository: EventRepository

	@Autowired
	private lateinit var participantRepository: ParticipantRepository

	@Autowired
	private lateinit var instructorRepository: InstructorRepository

	@Bean
	@Profile("!test")
	fun databaseInitializer() = ApplicationRunner {

		val instructor1 = instructorRepository.save(Instructor(name = "Mark", profession = "Social Media Specialist"))
		val instructor2 = instructorRepository.save(Instructor(name = "Sam Newman", profession = "Software Architect"))

		val participant1 = participantRepository.save(Participant(name = "Ronald"))
		val participant2 = participantRepository.save(Participant(name = "Donald"))
		val participant3 = participantRepository.save(Participant(name = "Antone"))
		val participant4 = participantRepository.save(Participant(name = "Jamie"))

		val participantList1 = mutableListOf(participant1, participant2, participant3)
		val participantList2 = mutableListOf(participant2, participant3, participant4)

		eventRepository.save(Event(
				title = "AI in Social Media",
				description = "Facebook uses advanced machine learning to do " +
						"everything from serve you content to recognize your face in photos to target " +
						"users with advertising. Instagram (owned by Facebook) uses AI to identify visuals.",
				instructor = instructor1,
				image = "dummy.jpg",
				date = LocalDate.now().plusDays(3),
				participants = participantList1)
		)

		eventRepository.save(
				Event(
						title = "Microservice Architecture",
						description = "A microservices architecture takes this same approach and extends " +
								"it to the loosely coupled services which can be developed, deployed, and maintained independently. Each of these " +
								"services is responsible for discrete task and can communicate with other services through simple APIs to solve a " +
								"larger complex business problem.",
						instructor = instructor2,
						image = "dummy.jpg",
						date = LocalDate.now().plusDays(4),
						participants = participantList2
				),
		)

	}

	@Bean
	fun createRestApi(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
	}

	private fun apiInfo(): ApiInfo {
		return ApiInfoBuilder()
				.title("Devs Meet Meetup Platform APIs")
				.description("Devs Meet")
				.termsOfServiceUrl("https://www.devsmeet.io")
				.contact(Contact("Devs Meet", "https://www.devsmeet.com", "info@devsmeet.com"))
				.version("1.0.0")
				.build()
	}

}

fun main(args: Array<String>) {
	runApplication<MeetupApplication>(*args)
}
