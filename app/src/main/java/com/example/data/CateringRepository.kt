package com.example.data

import com.example.data.dao.CateringDao
import com.example.data.entity.BookingOrder
import com.example.data.entity.ComboPackage
import com.example.data.entity.StaffMember
import com.example.data.entity.UniformItem
import kotlinx.coroutines.flow.Flow

class CateringRepository(private val dao: CateringDao) {

    val allStaff: Flow<List<StaffMember>> = dao.getAllStaff()
    fun getStaffByRole(role: String): Flow<List<StaffMember>> = dao.getStaffByRole(role)

    suspend fun insertStaff(staff: StaffMember) = dao.insertStaff(staff)
    suspend fun updateStaff(staff: StaffMember) = dao.updateStaff(staff)
    suspend fun deleteStaff(id: Int) = dao.deleteStaff(id)

    val allUniforms: Flow<List<UniformItem>> = dao.getAllUniforms()
    fun getUniformsByCategory(category: String): Flow<List<UniformItem>> = dao.getUniformsByCategory(category)

    suspend fun insertUniform(uniform: UniformItem) = dao.insertUniform(uniform)
    suspend fun updateUniform(uniform: UniformItem) = dao.updateUniform(uniform)
    suspend fun deleteUniform(id: Int) = dao.deleteUniform(id)

    val comboPackages: Flow<List<ComboPackage>> = dao.getAllComboPackages()

    val allBookings: Flow<List<BookingOrder>> = dao.getAllBookings()
    suspend fun insertBooking(booking: BookingOrder): Long = dao.insertBooking(booking)
    suspend fun updateBookingStatus(id: Int, status: String) = dao.updateBookingStatus(id, status)
    suspend fun assignStaffToBooking(id: Int, assignedStaff: String) = dao.assignStaffToBooking(id, assignedStaff)
    suspend fun deleteBooking(id: Int) = dao.deleteBooking(id)
}
