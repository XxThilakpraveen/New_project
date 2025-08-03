import java.time.Duration
import java.time.LocalDateTime

class EmployeeManager {
    private val employeeList = EmployeeList()
    private val attendanceList = AttendanceList()
    private var counter = 1

    init {
        addEmployee("John", "Doe", Role.DEVELOPER, "Engineering", "AS002") // Fixed reportingTo
        addEmployee("Alice", "Smith", Role.MANAGER, "Engineering", "CJ005")
        addEmployee("Jackson", "Brown", Role.MANAGER, "Engineering", "CJ005")
        addEmployee("Bob", "Johnson", Role.DEVELOPER, "Engineering", "AS002")
        addEmployee("Clara", "Jones", Role.CEO, "Leadership", null)
    }

    private fun generateId(firstName: String, lastName: String): String {
        val first = firstName.trim().firstOrNull()?.uppercaseChar() ?: 'X'
        val last = lastName.trim().lastOrNull()?.uppercaseChar() ?: 'Y'
        return "$first$last${String.format("%03d", counter++)}"
    }

    fun addEmployee(firstName: String, lastName: String, role: Role, dept: String, reportingTo: String?): Boolean {
        val id = generateId(firstName, lastName)
        val emp = Employee(id, firstName.trim(), lastName.trim(), role, dept.trim(), reportingTo?.trim())
        return employeeList.add(emp)
    }

    fun updateEmployee(id: String, firstName: String, lastName: String, role: Role, dept: String, reportingTo: String?): Boolean {
        val emp = Employee(id, firstName.trim(), lastName.trim(), role, dept.trim(), reportingTo?.trim())
        return employeeList.update(emp)
    }

    fun deleteEmployee(id: String): Boolean {
        return employeeList.delete(id)
    }

    fun checkIn(id: String, checkInDateTime: LocalDateTime): Boolean {
        val employeeExists = employeeList.list.any { it.id == id }
        if (!employeeExists) {
            println("Check-in Failed: Employee ID $id not found.")
            return false
        }

        val hasOpenAttendance = attendanceList.list.any { it.id == id && it.checkOutDateTime == null }
        if (hasOpenAttendance) {
            println("Check-in Failed: Employee ID $id is already checked in.")
            return false
        }

        val attendance = Attendance(id, checkInDateTime)
        return attendanceList.add(attendance)
    }

    // Corrected `checkOut` method
    fun checkOut(id: String, checkOutDateTime: LocalDateTime): Boolean {
        val openAttendance = attendanceList.list.find { it.id == id && it.checkOutDateTime == null }

        if (openAttendance == null) {
            println("Check-out Failed: No active check-in found for ID $id.")
            return false
        }
//        openAttendance.checkOutDateTime = checkOutDateTime
        return attendanceList.update(openAttendance, checkOutDateTime)

    }


    fun deleteInvalidAttendanceById(id: String): Boolean {
        val invalidRecord = attendanceList.list.find {
            it.id == id && it.checkInDateTime == it.checkOutDateTime
        }

        return if (invalidRecord != null) {
            attendanceList.delete(invalidRecord.id, invalidRecord.checkInDateTime)
        } else {
            println("No invalid attendance record found for ID $id.")
            false
        }
    }

    fun listActiveAttendances(): String {
        val active = attendanceList.getActiveAttendances()
        return if (active.isEmpty()) "No employees currently checked in."
        else active.joinToString("\n") { it.toString() }
    }

    fun listAttendancesBetween(from: LocalDateTime, to: LocalDateTime): String {
        val filtered = attendanceList.getAttendancesBetween(from, to)
        return if (filtered.isEmpty()) "No attendance records found in the given range."
        else filtered.joinToString("\n") { it.toString() }
    }

    fun printEmployee(): String {
        return employeeList.toString()
    }

    fun printAttendance(): String {
        return attendanceList.toString()
    }
}