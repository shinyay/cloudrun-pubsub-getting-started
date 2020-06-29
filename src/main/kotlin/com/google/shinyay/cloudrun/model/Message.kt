package com.google.shinyay.cloudrun.model

data class Message(val messageId: String,
                   val publishTime: String,
                   val data: String)