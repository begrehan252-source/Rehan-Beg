package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CateringRepository
import com.example.data.entity.BookingOrder
import com.example.data.entity.StaffMember
import com.example.data.entity.UniformItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CateringViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CateringRepository
    val isManagerMode = MutableStateFlow(false)

    // Filters
    val selectedRoleFilter = MutableStateFlow("All")
    val selectedUniformCategoryFilter = MutableStateFlow("All")

    init {
        val db = AppDatabase.getDatabase(application)
        repository = CateringRepository(db.cateringDao())
    }

    val staffList: StateFlow<List<StaffMember>> = combine(
        repository.allStaff,
        selectedRoleFilter
    ) { staff, filter ->
        if (filter == "All") staff
        else staff.filter { it.role.equals(filter, ignoreCase = true) || it.role.contains(filter, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val uniformList: StateFlow<List<UniformItem>> = combine(
        repository.allUniforms,
        selectedUniformCategoryFilter
    ) { uniforms, filter ->
        if (filter == "All") uniforms
        else uniforms.filter { it.category.equals(filter, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val packageList = repository.comboPackages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val bookingList = repository.allBookings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // User Message state
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage = _userMessage.asStateFlow()

    fun clearUserMessage() {
        _userMessage.value = null
    }

    // Cost Estimator State
    val estimatorGuestCount = MutableStateFlow(100)
    val estimatorDurationHours = MutableStateFlow(6)
    val estimatorNeedsCaptain = MutableStateFlow(true)
    val estimatorSelectedUniform = MutableStateFlow("Formal Suits")
    val estimatorNeedsUniforms = MutableStateFlow(true)

    fun createBooking(
        clientName: String,
        clientPhone: String,
        eventType: String,
        eventDate: String,
        eventTime: String,
        eventDurationHours: Int,
        venueAddress: String,
        waiterCount: Int,
        selectedUniformCategory: String,
        uniformCount: Int,
        uniformSizes: String,
        totalAmount: Double,
        securityDeposit: Double
    ) {
        viewModelScope.launch {
            val booking = BookingOrder(
                clientName = clientName.ifBlank { "Guest Host" },
                clientPhone = clientPhone.ifBlank { "+91 99999 00000" },
                eventType = eventType,
                eventDate = eventDate.ifBlank { "Upcoming Date" },
                eventTime = eventTime.ifBlank { "06:00 PM" },
                eventDurationHours = eventDurationHours,
                venueAddress = venueAddress.ifBlank { "Event Venue Address" },
                waiterCount = waiterCount,
                selectedUniformCategory = selectedUniformCategory,
                uniformCount = uniformCount,
                uniformSizes = uniformSizes.ifBlank { "L & M Standard" },
                assignedStaffNames = "Pending Assignment",
                totalAmount = totalAmount,
                securityDeposit = securityDeposit,
                status = "Pending"
            )
            repository.insertBooking(booking)
            _userMessage.value = "Booking request submitted successfully!"
        }
    }

    fun updateBookingStatus(id: Int, newStatus: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(id, newStatus)
            _userMessage.value = "Order status updated to $newStatus"
        }
    }

    fun assignStaffToBooking(id: Int, staffNames: String) {
        viewModelScope.launch {
            repository.assignStaffToBooking(id, staffNames)
            repository.updateBookingStatus(id, "Staff Assigned")
            _userMessage.value = "Staff assigned to booking #$id"
        }
    }

    fun cancelBooking(id: Int) {
        viewModelScope.launch {
            repository.updateBookingStatus(id, "Cancelled")
            _userMessage.value = "Booking #$id has been cancelled."
        }
    }

    // Manager / Provider Inventory Operations
    fun addNewStaff(
        name: String,
        role: String,
        expYears: Int,
        dailyRate: Double,
        phone: String,
        languages: String,
        skill: String
    ) {
        viewModelScope.launch {
            val staff = StaffMember(
                name = name,
                role = role,
                experienceYears = expYears,
                hourlyRate = dailyRate / 6.0,
                dailyRate = dailyRate,
                rating = 4.8,
                phone = phone,
                languages = languages.ifBlank { "Hindi, English" },
                specialSkill = skill.ifBlank { "General Waiter & Food Serving" },
                isAvailable = true
            )
            repository.insertStaff(staff)
            _userMessage.value = "New staff member '$name' added!"
        }
    }

    fun addNewUniform(
        title: String,
        category: String,
        sizes: String,
        dailyRent: Double,
        deposit: Double,
        stock: Int,
        desc: String,
        includes: String
    ) {
        viewModelScope.launch {
            val item = UniformItem(
                title = title,
                category = category,
                sizesAvailable = sizes.ifBlank { "M, L, XL" },
                dailyRentalPrice = dailyRent,
                securityDepositPerItem = deposit,
                totalStock = stock,
                availableStock = stock,
                description = desc.ifBlank { "Clean sanitized catering uniform" },
                includes = includes.ifBlank { "Full Uniform Set" }
            )
            repository.insertUniform(item)
            _userMessage.value = "New uniform item '$title' added!"
        }
    }

    fun toggleStaffAvailability(staff: StaffMember) {
        viewModelScope.launch {
            repository.updateStaff(staff.copy(isAvailable = !staff.isAvailable))
        }
    }
}
