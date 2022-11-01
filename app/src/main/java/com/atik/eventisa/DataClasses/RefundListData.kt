package com.atik.eventisa.DataClasses

data class RefundListData(var imageUri:String?=null,
                          val eventTitle:String?=null,
                          val eventDate:String?=null,
                          val eventLocation:String?=null,
                          val eventDescription:String?=null,
                          val eventId:String?=null,
                          val eventPrice: Int ?=null,
                          val eventSeat:Long?=null,
                          val hostEmail:String?=null,
                          var firstName:String?=null,
                          var lastName:String?=null,
                          var Email:String?=null,
                          var phoneNumber:String?=null,
                          var userName:String?=null,
                          var uid:String?=null)
