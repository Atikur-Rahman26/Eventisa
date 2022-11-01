package com.atik.eventisa

data class AddEventData(
    var imageUri:String?=null,
    val eventTitle:String?=null,
    val eventDate:String?=null,
    val eventLocation:String?=null,
    val eventDescription:String?=null,
    val eventId:String?=null,
    val eventPrice: Int ?=null,
    val eventSeat:Long?=null,
    val hostEmail:String?=null
)
