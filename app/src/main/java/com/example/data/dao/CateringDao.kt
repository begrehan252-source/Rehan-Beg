package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.BookingOrder
import com.example.data.entity.ComboPackage
import com.example.data.entity.StaffMember
import com.example.data.entity.UniformItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CateringDao {
    // Staff Queries
    @Query("SELECT * FROM staff_members ORDER BY rating DESC")
    fun getAllStaff(): Flow<List<StaffMember>>

    @Query("SELECT * FROM staff_members WHERE role = :role ORDER BY rating DESC")
    fun getStaffByRole(role: String): Flow<List<StaffMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: StaffMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaffList(staffList: List<StaffMember>)

    @Update
    suspend fun updateStaff(staff: StaffMember)

    @Query("DELETE FROM staff_members WHERE id = :id")
    suspend fun deleteStaff(id: Int)

    // Uniform Queries
    @Query("SELECT * FROM uniform_items ORDER BY id ASC")
    fun getAllUniforms(): Flow<List<UniformItem>>

    @Query("SELECT * FROM uniform_items WHERE category = :category")
    fun getUniformsByCategory(category: String): Flow<List<UniformItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniform(uniform: UniformItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniformList(uniforms: List<UniformItem>)

    @Update
    suspend fun updateUniform(uniform: UniformItem)

    @Query("DELETE FROM uniform_items WHERE id = :id")
    suspend fun deleteUniform(id: Int)

    // Combo Package Queries
    @Query("SELECT * FROM combo_packages ORDER BY dailyPrice ASC")
    fun getAllComboPackages(): Flow<List<ComboPackage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComboPackages(packages: List<ComboPackage>)

    // Booking Queries
    @Query("SELECT * FROM booking_orders ORDER BY bookingTimestamp DESC")
    fun getAllBookings(): Flow<List<BookingOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingOrder): Long

    @Query("UPDATE booking_orders SET status = :status WHERE id = :id")
    suspend fun updateBookingStatus(id: Int, status: String)

    @Query("UPDATE booking_orders SET assignedStaffNames = :assignedStaff WHERE id = :id")
    suspend fun assignStaffToBooking(id: Int, assignedStaff: String)

    @Query("DELETE FROM booking_orders WHERE id = :id")
    suspend fun deleteBooking(id: Int)
}
