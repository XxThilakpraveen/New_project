import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException
import kotlin.random.Random

val employeeDatabase = mutableListOf<DataEmployee>()
val checkInAttendanceList = mutableListOf<DataAttendance>()

// Data Classes
data class DataEmployee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: String
)

data class DataAttendance(
    val id: String,
    val checkinDateTime: LocalDateTime,
    var checkoutDateTime: LocalDateTime? = null
)

// Employee Class
class EmployeeClass {

    fun createEmp(firstName: String, lastName: String, role: String): DataEmployee {
        val generatedId = generateUniqueId(firstName, lastName)
        val newEmp = DataEmployee(generatedId, firstName, lastName, role)
        employeeDatabase.add(newEmp)
        println("User created: ID=${newEmp.id}, Name=${newEmp.firstName} ${newEmp.lastName}, Role=${newEmp.role}")
        return newEmp
    }

    private fun generateUniqueId(firstName: String, lastName: String): String {
        var idString: String
        do {
            val prefix = "${firstName.firstOrNull()?.uppercaseChar() ?: 'X'}${lastName.lastOrNull()?.uppercaseChar() ?: 'Z'}"
            val randomNumber = Random.nextInt(100, 999)
            idString = prefix + randomNumber
        } while (employeeDatabase.any { it.id == idString })
        return idString
    }
    fun listAllEmployees() {
        println("\n--- Employee List ---")
        if (employeeDatabase.isEmpty()) {
            println("No employees found.")
        } else {
            employeeDatabase.forEach {
                println("ID: ${it.id}, Name: ${it.firstName} ${it.lastName}, Role: ${it.role}")
            }
        }
    }

}

// Attendance Class
class AttendanceClass {

    fun dailyCheckIn(emp: DataEmployee, id: String, date: LocalDateTime = LocalDateTime.now()): Boolean {
        val existingEmp = findEmployeeInEmpList(id)
        return if (existingEmp != null) {
            if (hasAlreadyCheckedIn(id)) {
                println("Employee with ID ${id} has already checked in.")
                false
            } else {
                addNewCheckin(emp, date)
                true
            }
        } else {
            println("Employee with ID ${id} does not exist in the system.")
            false
        }
    }
    fun getDateTime(): LocalDateTime {
        val input = readLine()
        return try {
            if (input.isNullOrBlank()) {
                LocalDateTime.now()
            } else {
                val parsedDate = LocalDate.parse(input)
                parsedDate.atTime(LocalTime.now())
            }
        } catch (e: DateTimeParseException) {
            println("Invalid format. Using the current date and time.")
            LocalDateTime.now()
        }
    }

    fun dailyCheckOut(id: String, checkoutTime: LocalDateTime) {
        val empToBeCheckedOut = getActiveCheckIn(id)
        val empCheckInDate = empToBeCheckedOut?.checkinDateTime

        if (empCheckInDate != null && checkoutTime.isBefore(empCheckInDate)) {
            println("You are trying to check out with an invalid date (before check-in).")
        }
        val checkinTime = empToBeCheckedOut?.checkinDateTime
        val duration = java.time.Duration.between(checkinTime, checkoutTime)
        if (duration.toHours() > 24) {
            println("You are trying to check out with a future time. Please enter a valid time.")
        }

        if (empToBeCheckedOut != null) {
            empToBeCheckedOut.checkoutDateTime = checkoutTime
            println("Employee with ID $id checked out at $checkoutTime, has worked for ${duration.toHours()} hours.")
        } else {
            println("No active check-in found for ID $id.")
        }
    }

    fun showAttendance() {
        println("\n--- Attendance List ---")
        checkInAttendanceList.forEach {
            println("ID: ${it.id}, Checkin: ${it.checkinDateTime}, Checkout: ${it.checkoutDateTime ?: "Still Checked-in"}")
        }
    }

    private fun hasAlreadyCheckedIn(id: String): Boolean {
        return checkInAttendanceList.any { it.id == id && it.checkoutDateTime == null }
    }

    private fun getActiveCheckIn(id: String): DataAttendance? {
        return checkInAttendanceList.find { it.id == id && it.checkoutDateTime == null }
    }

    private fun findEmployeeInEmpList(id: String): DataEmployee? {
        return employeeDatabase.find { it.id == id }
    }

    private fun addNewCheckin(emp: DataEmployee, currentTime: LocalDateTime) {
        val empAttendance = DataAttendance(emp.id, currentTime)
        checkInAttendanceList.add(empAttendance)
        println("Employee ${emp.firstName} ${emp.lastName} checked in at $currentTime.")
    }
}

fun getValidEmployeeIdOrNull(): DataEmployee? {
    print("Enter ID: ")
    val id = readLine()?.trim() ?: return null
    val emp = employeeDatabase.find { it.id == id }
    if (emp == null) {
        println("No employee found with ID $id")
    }
    return emp
}


fun main() {
    val employeeManager = EmployeeClass()
    val attendanceManager = AttendanceClass()

    employeeManager.createEmp("Bruce", "Wayne", "Manager")
    employeeManager.createEmp("Subash", "Smith", "CEO")
    employeeManager.createEmp("Tony", "Stark", "Developer")
    employeeManager.createEmp("Charles", "Xavier", "Developer")


    while (true) {
        println("\nEnter \n 1 -> checkin \n 2 -> checkout \n 3 -> ShowAttendance \n 4 -> ShowEmpList \n 5 -> exit")
        val action = readLine()?.trim() ?: ""

        if (action == "5") {
            break
        }

        if (action != "1" && action != "2" && action != "3" && action != "4") {
            println("Invalid action. Please enter '1', '2', '3', '4', or '5'.")
            continue
        }


        when (action) {
            "1" -> {
                val emp = getValidEmployeeIdOrNull() ?: continue
                println("Enter check-in date (yyyy-MM-dd) or press Enter to use current time: ")
                val checkInTime = attendanceManager.getDateTime()
                attendanceManager.dailyCheckIn(emp, emp.id, checkInTime)
                attendanceManager.showAttendance()
            }
            "2" -> {
                val emp = getValidEmployeeIdOrNull() ?: continue
                println("Enter checkout date (yyyy-MM-dd) or press Enter to use current time: ")
                val checkOutTime = attendanceManager.getDateTime()
                attendanceManager.dailyCheckOut(emp.id, checkOutTime)
                attendanceManager.showAttendance()
            }
            "3" -> {
                attendanceManager.showAttendance()
            }
            "4" -> {
                employeeManager.listAllEmployees()
            }
        }
    }
}
