package com.google.shinyay.cloudrun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PubsubGettingStartedApplication

fun main(args: Array<String>) {
	runApplication<PubsubGettingStartedApplication>(*args)
}
