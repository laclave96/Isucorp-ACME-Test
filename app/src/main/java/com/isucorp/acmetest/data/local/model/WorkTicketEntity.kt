package com.isucorp.acmetest.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "work_ticket")
data class WorkTicketEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "customer_name")
    val customerName: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: Int,
    @ColumnInfo(name = "scheduled_time")
    val scheduledTime: Long,
    @ColumnInfo(name = "job_street")
    val jobStreet: String,
    @ColumnInfo(name = "job_city")
    val jobCity: String,
    @ColumnInfo(name = "job_postal_code")
    val jobPostalCode: String,
    val fee: Int,
    @ColumnInfo(name = "job_description")
    val jobDescription: String,
    @ColumnInfo(name = "dept_class")
    val deptClass: String,
    @ColumnInfo(name = "service_type")
    val serviceType: String,
    @ColumnInfo(name = "reason_for_call")
    val reasonForCall: String

)