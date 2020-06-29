package com.google.shinyay.cloudrun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PubsubGettingStartedApplication

fun main(args: Array<String>) {
	System.setProperty("server.port", System.getenv().getOrDefault("PORT", "8080"))
	runApplication<PubsubGettingStartedApplication>(*args)
}
