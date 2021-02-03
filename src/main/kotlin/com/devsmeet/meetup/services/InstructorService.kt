package com.devsmeet.meetup.services

import com.devsmeet.meetup.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InstructorService(private val instructorRepository: InstructorRepository,
                        private val instructorMapper: InstructorMapper) {

    fun getInstructorList(): List<InstructorDto> = instructorRepository.findAll().map {
        instructorMapper.convertToDto(it)
    }

    fun createInstructor(instructorDto: InstructorDto) = instructorRepository.save(instructorMapper.convertToEntity(instructorDto))

    fun getInstructorById(id: Long): InstructorDto {
        val instructor = isInstructorExist(id)
        return instructorMapper.convertToDto(instructor)
    }

    fun updateInstructor(instructorDto: InstructorDto) {
        val instructor = isInstructorExist(instructorDto.id!!)
        instructor.name = instructorDto.name
        instructor.profession = instructorDto.profession

        instructorRepository.save(instructor)
    }

    fun deleteInstructorById(id: Long) {
        isInstructorExist(id)
        instructorRepository.deleteById(id)
    }

    private fun isInstructorExist(id: Long): Instructor = instructorRepository.findById(id).orElseThrow {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, Exception_Instructor_Not_Found)
    }
}