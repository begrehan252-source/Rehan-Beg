package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.CateringDao
import com.example.data.entity.BookingOrder
import com.example.data.entity.ComboPackage
import com.example.data.entity.StaffMember
import com.example.data.entity.UniformItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [StaffMember::class, UniformItem::class, ComboPackage::class, BookingOrder::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cateringDao(): CateringDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "servimate_catering_db"
                )
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        seedInitialData(database.cateringDao())
                    }
                }
            }

            private suspend fun seedInitialData(dao: CateringDao) {
                // Seed Staff Members
                val initialStaff = listOf(
                    StaffMember(
                        name = "Rajesh Kumar",
                        role = "Head Captain",
                        experienceYears = 7,
                        hourlyRate = 350.0,
                        dailyRate = 2200.0,
                        rating = 4.9,
                        phone = "+91 98765 12345",
                        languages = "Hindi, English",
                        specialSkill = "5-Star Hotel Banquet Management, VIP Guest Care",
                        isAvailable = true
                    ),
                    StaffMember(
                        name = "Vikram Sharma",
                        role = "Senior Waiter",
                        experienceYears = 4,
                        hourlyRate = 250.0,
                        dailyRate = 1500.0,
                        rating = 4.8,
                        phone = "+91 98123 45678",
                        languages = "Hindi, English, Punjabi",
                        specialSkill = "Formal Buffet Setup & Silver Service",
                        isAvailable = true
                    ),
                    StaffMember(
                        name = "Amit Verma",
                        role = "Server / Waiter",
                        experienceYears = 3,
                        hourlyRate = 220.0,
                        dailyRate = 1350.0,
                        rating = 4.7,
                        phone = "+91 97654 32109",
                        languages = "Hindi",
                        specialSkill = "Speedy Snack Serving & Beverage Refilling",
                        isAvailable = true
                    ),
                    StaffMember(
                        name = "Rohan Gupta",
                        role = "Bartender",
                        experienceYears = 5,
                        hourlyRate = 400.0,
                        dailyRate = 2500.0,
                        rating = 4.9,
                        phone = "+91 99887 76655",
                        languages = "Hindi, English",
                        specialSkill = "Mocktail & Cocktail Mixing, Flair Bartending",
                        isAvailable = true
                    ),
                    StaffMember(
                        name = "Pooja Roy",
                        role = "Female Hostess / Server",
                        experienceYears = 3,
                        hourlyRate = 280.0,
                        dailyRate = 1700.0,
                        rating = 4.8,
                        phone = "+91 95432 10987",
                        languages = "Hindi, English",
                        specialSkill = "Welcome Drinks & Dessert Counter Host",
                        isAvailable = true
                    ),
                    StaffMember(
                        name = "Sunil Yadav",
                        role = "Buffet Manager",
                        experienceYears = 6,
                        hourlyRate = 300.0,
                        dailyRate = 1800.0,
                        rating = 4.8,
                        phone = "+91 91234 56789",
                        languages = "Hindi",
                        specialSkill = "Live Counter Management & Refill Coordination",
                        isAvailable = true
                    ),
                    StaffMember(
                        name = "Manoj Paswan",
                        role = "Cleaner & Helper",
                        experienceYears = 2,
                        hourlyRate = 150.0,
                        dailyRate = 950.0,
                        rating = 4.6,
                        phone = "+91 90112 23344",
                        languages = "Hindi",
                        specialSkill = "Table Clearance & Crockery Washing",
                        isAvailable = true
                    )
                )
                dao.insertStaffList(initialStaff)

                // Seed Uniform Items
                val initialUniforms = listOf(
                    UniformItem(
                        title = "Male Formal Waiter Suit & Bowtie",
                        category = "Formal Suits",
                        sizesAvailable = "S, M, L, XL, XXL",
                        dailyRentalPrice = 300.0,
                        securityDepositPerItem = 100.0,
                        totalStock = 50,
                        availableStock = 42,
                        description = "Classic black waistcoat, formal trousers, crisp white shirt, red/black bowtie & white gloves.",
                        includes = "Black Waistcoat, Black Pants, White Shirt, Bowtie, White Satin Gloves"
                    ),
                    UniformItem(
                        title = "Royal Silk Kurta Pajama Staff Attire",
                        category = "Indian Traditional",
                        sizesAvailable = "M, L, XL",
                        dailyRentalPrice = 350.0,
                        securityDepositPerItem = 150.0,
                        totalStock = 40,
                        availableStock = 35,
                        description = "Golden/Maroon silk ethnic kurta with Nehru jacket and matching pajama for traditional Indian weddings.",
                        includes = "Silk Kurta, Churidar Pajama, Brocade Nehru Jacket"
                    ),
                    UniformItem(
                        title = "Black Satin Waistcoat & Tie Set",
                        category = "Waistcoat Sets",
                        sizesAvailable = "S, M, L, XL",
                        dailyRentalPrice = 200.0,
                        securityDepositPerItem = 80.0,
                        totalStock = 60,
                        availableStock = 55,
                        description = "V-neck black satin waistcoat with adjustable back strap and matching formal tie.",
                        includes = "Satin Waistcoat, Black Tie, Name Tag Holder"
                    ),
                    UniformItem(
                        title = "Catering Apron & Chef/Server Cap Set",
                        category = "Aprons & Caps",
                        sizesAvailable = "Universal Fit",
                        dailyRentalPrice = 100.0,
                        securityDepositPerItem = 50.0,
                        totalStock = 100,
                        availableStock = 90,
                        description = "Water-resistant heavy cotton apron with front pockets and matching adjustable chef cap.",
                        includes = "Full Length Apron, Chef/Server Cap"
                    ),
                    UniformItem(
                        title = "Female Housekeeping & Server Uniform",
                        category = "Female Uniforms",
                        sizesAvailable = "S, M, L, XL",
                        dailyRentalPrice = 280.0,
                        securityDepositPerItem = 100.0,
                        totalStock = 30,
                        availableStock = 28,
                        description = "Neat dark blue tunic with white collar trim and matching formal trousers.",
                        includes = "Tunic Top, Pants, Hair Net Set"
                    ),
                    UniformItem(
                        title = "VIP Butler Tuxedo Suit",
                        category = "Formal Suits",
                        sizesAvailable = "M, L, XL",
                        dailyRentalPrice = 500.0,
                        securityDepositPerItem = 250.0,
                        totalStock = 15,
                        availableStock = 12,
                        description = "Premium black satin lapel tuxedo jacket, cummerbund, formal shirt and bowtie for high-end events.",
                        includes = "Tuxedo Blazer, Pants, Shirt, Cummerbund, Satin Bowtie"
                    )
                )
                dao.insertUniformList(initialUniforms)

                // Seed Combo Packages
                val initialPackages = listOf(
                    ComboPackage(
                        packageName = "Small House Party Pack",
                        tagLine = "Ideal for Birthdays & Family Dinner Parties",
                        waiterCount = 2,
                        captainCount = 0,
                        uniformSetIncluded = "2x Male Formal Waistcoat & Bowtie Sets",
                        dailyPrice = 3100.0,
                        originalPrice = 3600.0,
                        bestFor = "20 to 40 Guests",
                        highlights = "Clean Sanitized Uniforms • 6 Hours Service • Food Serving & Table Clearance"
                    ),
                    ComboPackage(
                        packageName = "Standard Wedding & Banquet Crew",
                        tagLine = "Complete Staffing & Uniform Solution for Events",
                        waiterCount = 5,
                        captainCount = 1,
                        uniformSetIncluded = "6x Formal Tuxedo Waistcoat Suits + White Gloves",
                        dailyPrice = 9800.0,
                        originalPrice = 11500.0,
                        bestFor = "80 to 150 Guests",
                        highlights = "1 Head Captain • 5 Trained Waiters • On-time On-site Delivery"
                    ),
                    ComboPackage(
                        packageName = "Royal Indian Wedding Pack",
                        tagLine = "Grand Traditional Ethnic Attire & Experienced Staff",
                        waiterCount = 10,
                        captainCount = 2,
                        uniformSetIncluded = "12x Royal Silk Kurta Pajama & Nehru Jacket Sets",
                        dailyPrice = 21500.0,
                        originalPrice = 25000.0,
                        bestFor = "200 to 400 Guests",
                        highlights = "2 Captains • 10 Senior Waiters • Traditional Welcome Setup • Full Day Service"
                    ),
                    ComboPackage(
                        packageName = "Corporate & VIP Lounge Package",
                        tagLine = "High-end Professional Waiters & Butler Service",
                        waiterCount = 4,
                        captainCount = 1,
                        uniformSetIncluded = "5x Premium VIP Butler Tuxedos & Bowties",
                        dailyPrice = 12500.0,
                        originalPrice = 14500.0,
                        bestFor = "Corporate Gala / VIP Summit",
                        highlights = "English Speaking Staff • Cocktail & Tray Service • Pristine Tuxedo Rentals"
                    )
                )
                dao.insertComboPackages(initialPackages)

                // Seed Sample Initial Booking Order so user sees live tracking on first launch
                val sampleBooking = BookingOrder(
                    clientName = "Anand Sharma",
                    clientPhone = "+91 98989 12345",
                    eventType = "Wedding Reception",
                    eventDate = "2026-08-15",
                    eventTime = "06:00 PM",
                    eventDurationHours = 8,
                    venueAddress = "Royal Oak Banquet Hall, Sector 18, Noida",
                    waiterCount = 5,
                    selectedUniformCategory = "Formal Suits",
                    uniformCount = 5,
                    uniformSizes = "3x L, 2x M",
                    assignedStaffNames = "Rajesh Kumar (Captain), Vikram Sharma, Amit Verma",
                    totalAmount = 9800.0,
                    securityDeposit = 500.0,
                    status = "Confirmed"
                )
                dao.insertBooking(sampleBooking)
            }
        }
    }
}
