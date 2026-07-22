package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff_members")
data class StaffMember(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val role: String, // Captain, Waiter, Bartender, Buffet Manager, Kitchen Helper
    val experienceYears: Int,
    val hourlyRate: Double,
    val dailyRate: Double,
    val rating: Double,
    val phone: String,
    val languages: String,
    val specialSkill: String,
    val isAvailable: Boolean = true,
    val profileImageUrl: String = ""
)
