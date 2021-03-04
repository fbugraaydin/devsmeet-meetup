package com.devsmeet.meetup.controller

import com.devsmeet.meetup.InstructorDto
import com.devsmeet.meetup.InstructorRequest
import com.devsmeet.meetup.services.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/instructors")
class InstructorController(private val instructorService: InstructorService) {

    @GetMapping
    fun listInstructors(): List<InstructorDto>  = instructorService.getInstructorList()

    @GetMapping("/{id}")
    fun getInstructorById(@PathVariable id: Long) = instructorService.getInstructorById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@Valid @RequestBody req: InstructorRequest) {
        instructorService.createInstructor(InstructorDto(name = req.name, profession = req.profession))
    }

    @PutMapping("/{id}")
    fun updateInstructor(@Valid @RequestBody req: InstructorRequest, @PathVariable id: Long) {
        instructorService.updateInstructor(InstructorDto(id = id, name = req.name, req.profession))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteInstructorById(@PathVariable id: Long) = instructorService.deleteInstructorById(id)

}