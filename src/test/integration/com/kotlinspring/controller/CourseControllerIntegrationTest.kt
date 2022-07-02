package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.CourseRepository
import com.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setup(){
        courseRepository.deleteAll()
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse(){
        val courseDTO = CourseDTO(null,"Build Restfull Api Using Sprintboot and Kotlin","Development")
       val savedCourseDTO =  webTestClient
            .post()
            .uri("v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue{
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses(){
        val courses = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody
        println("CourseDTOS : $courses")
        assertEquals(3,courses!!.size)
    }

    @Test
    fun updateCourse() {
        val course =     Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")
        courseRepository.save(course)

       val updatedCourseDTO = CourseDTO(null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development")
       val updateCourse = webTestClient
            .put()
            .uri("v1/courses/{course_id}",course.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Build RestFul APis using SpringBoot and Kotlin1",updateCourse!!.name)

    }

    @Test
    fun deleteCourse() {
        val course =     Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")
        courseRepository.save(course)

        val updatedCourseDTO = CourseDTO(null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development")
        val updateCourse = webTestClient
            .delete()
            .uri("v1/courses/{course_id}",course.id)
            .exchange()
            .expectStatus().isNoContent

    }
}