import java.time.LocalDateTime

class EmployeeManager {
    private val employeeList = EmployeeList()
    private val attendanceList = AttendanceList()

    init {
        addEmployee("John", "Doe", Role.DEVELOPER, "Engineering", "AS002")
        addEmployee("Alice", "Smith", Role.MANAGER, "Engineering", "CJ005")
        addEmployee("Jackson", "Brown", Role.MANAGER, "Engineering", "CJ005")
        addEmployee("Bob", "Johnson", Role.DEVELOPER, "Engineering", "AS002")
        addEmployee("Clara", "Jones", Role.CEO, "Leadership", null)
    }

    fun addEmployee(firstName: String, lastName: String, role: Role, dept: String, reportingTo: String?): Boolean {
        val emp = Employee(firstName.trim(), lastName.trim(), role, dept.trim(), reportingTo?.trim())
        return employeeList.add(emp)
    }

    fun updateEmployee(id: String, firstName: String, lastName: String, role: Role, dept: String, reportingTo: String?): Boolean {
        val emp = employeeList.find { it.id == id }
        return if (emp != null) {
            emp.firstName = firstName.trim()
            emp.lastName = lastName.trim()
            emp.role = role
            emp.department = dept.trim()
            emp.reportingTo = reportingTo?.trim()
            emp.isValid()
        } else {
            println("Employee with ID $id not found.")
            false
        }
    }

    fun deleteEmployee(id: String): Boolean {
        return employeeList.removeIf { it.id == id }
    }

    fun checkIn(id: String, checkInDateTime: LocalDateTime): Boolean {
        val employeeExists = employeeList.any { it.id == id }
        if (!employeeExists) {
            println("Check-in Failed: Employee ID $id not found.")
            return false
        }

        // New: Check if employee already checked in today
        val alreadyCheckedInToday = attendanceList.any { att -> att.id == id && att.checkInDateTime.toLocalDate() == checkInDateTime.toLocalDate()
        }
        if (alreadyCheckedInToday) {
            println("Check-in Failed: Employee ID $id has already checked in today.")
            return false
        }

        val hasOpenAttendance = attendanceList.any { it.id == id && it.checkOutDateTime == null }
        if (hasOpenAttendance) {
            println("Check-in Failed: Employee ID $id is already checked in.")
            return false
        }

        val attendance = Attendance(id, checkInDateTime)
        return attendanceList.add(attendance)
    }


    fun checkOut(id: String, checkOutDateTime: LocalDateTime): Boolean {
        val openAttendance = attendanceList.find { it.id == id && it.checkOutDateTime == null }

        if (openAttendance == null) {
            println("Check-out Failed: No active check-in found for ID $id.")
            return false
        }
        return attendanceList.update(openAttendance, checkOutDateTime)
    }

    fun deleteInvalidAttendanceById(id: String): Boolean {
        val invalidRecord = attendanceList.find {
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
        return attendanceList.getAttendancesBetween(from, to)
    }

    fun printEmployee(): String {
        return employeeList.toString()
    }

    fun printAttendance(): String {
        return attendanceList.toString()
    }
}
