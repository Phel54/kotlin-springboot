package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.repository.InstructorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @Test
    fun addInstructor(){
        val instructorDTO = InstructorDTO(null,"Isichei Phelim")
       val savedInstructor = webTestClient
            .post()
            .uri("v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue{
            savedInstructor!!.id != null
        }
    }




}