package com.kotlinspring.controller

import com.kotlinspring.service.GreetingsService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingsController (
    val greetingsService: GreetingsService
        ){
    companion object : KLogging()


    @GetMapping("/{name}")
    fun retrieveGreetings(@PathVariable("name") name: String): String{
//        return "Hello $name"
        logger.info("Name is $name")
        return greetingsService.retrieveGreetings(name)
    }
}