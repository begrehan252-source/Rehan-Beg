package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "combo_packages")
data class ComboPackage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val packageName: String,
    val tagLine: String,
    val waiterCount: Int,
    val captainCount: Int,
    val uniformSetIncluded: String,
    val dailyPrice: Double,
    val originalPrice: Double,
    val bestFor: String, // e.g., "50-100 Guests Wedding / Reception"
    val highlights: String
)
