package com.google.shinyay.cloudrun.controller

import com.google.shinyay.cloudrun.model.Body
import com.google.shinyay.cloudrun.model.Message
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class PubSubController {

    @PostMapping("/")
    fun receiveMessage(@RequestBody body: Body?): ResponseEntity<Any?> {

        val message: Message? = body?.message
        message?.let {
            val msg = "Bad Request: invalid Pub/Sub message format"
            println("Bad Request: invalid Pub/Sub message format")
            return ResponseEntity<Any?>(msg, HttpStatus.BAD_REQUEST)
        }
        val data = message!!.data
        val target = if (!StringUtils.isEmpty(data)) String(Base64.getDecoder().decode(data)) else "World"
        val msg = "Hello $target!"

        println(msg)
        return ResponseEntity(msg, HttpStatus.OK)

    }
}