package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uniform_items")
data class UniformItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String, // Formal Tuxedos, Waistcoat Sets, Indian Traditional, Aprons & Caps, Housekeeping
    val sizesAvailable: String, // S, M, L, XL, XXL
    val dailyRentalPrice: Double,
    val securityDepositPerItem: Double,
    val totalStock: Int,
    val availableStock: Int,
    val description: String,
    val includes: String // e.g. "Includes Jacket, Pants, White Shirt & Black Bowtie"
)
