package com.isucorp.acmetest.presentation.model

data class WorkTicketItem(
    val id: Int,
    val time: String,
    val date: String,
    val serviceType:String,
    val jobStreet: String,
    val jobCity: String,
    val jobPostalCode: String,
)

