package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_orders")
data class BookingOrder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientName: String,
    val clientPhone: String,
    val eventType: String, // Wedding, Corporate, Birthday, Family Party, Banquet
    val eventDate: String,
    val eventTime: String,
    val eventDurationHours: Int,
    val venueAddress: String,
    val waiterCount: Int,
    val selectedUniformCategory: String,
    val uniformCount: Int,
    val uniformSizes: String,
    val assignedStaffNames: String,
    val totalAmount: Double,
    val securityDeposit: Double,
    val status: String, // Pending, Confirmed, Staff Assigned, Out for Event, Completed, Cancelled
    val bookingTimestamp: Long = System.currentTimeMillis()
)
