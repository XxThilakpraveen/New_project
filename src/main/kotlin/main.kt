import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException
import kotlin.random.Random

val employeeDatabase = mutableListOf<Employee>()
val checkInAttendanceList = mutableListOf<Attendance>()

// Data Classes
data class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: String
)

data class Attendance(
    val id: String,
    val checkinDateTime: LocalDateTime,
    var checkoutDateTime: LocalDateTime? = null
)

// Employee Class
class EmployeeManager {

    fun createEmp(firstName: String, lastName: String, role: String): Employee {
        val generatedId = generateUniqueId(firstName, lastName)
        val newEmp = Employee(generatedId, firstName, lastName, role)
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

    fun reportTo(emp: Employee) {
        val empId = emp.id
        val empRole = emp.role

        if (empRole == "Developer") {
            val managers = employeeDatabase.filter { it.role == "Manager" }
            if (managers.isNotEmpty()) {
                println("\nEmployee of ID $empId of role $empRole reports to the following manager(s):")
                managers.forEach { println("Manager ID: ${it.id}, Name: ${it.firstName} ${it.lastName}") }
            } else println("\nManager not found")
        } else if (empRole == "Manager") {
            val ceos = employeeDatabase.filter { it.role == "CEO" }
            if (ceos.isNotEmpty()) {
                println("\nEmployee of ID $empId of role $empRole reports to CEO:")
                ceos.forEach { println("CEO ID: ${it.id}, Name: ${it.firstName} ${it.lastName}") }
            } else println("\nNo CEO found in the org")
        } else {
            println("\nCEO reports to no one")
        }
    }
}

// Attendance Class
class AttendanceManager {

    fun dailyCheckIn(emp: Employee, id: String, date: LocalDateTime = LocalDateTime.now()): Boolean {
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

    fun dailyCheckOut(id: String, checkoutTime: LocalDateTime) {
        val empToBeCheckedOut = getActiveCheckIn(id)
        val empCheckInDate = empToBeCheckedOut?.checkinDateTime

        if (empCheckInDate != null && checkoutTime.isBefore(empCheckInDate)) {
            println("You are trying to check out with an invalid date (before check-in).")
            return
        }
        if (checkoutTime.isAfter(LocalDateTime.now())) {
            println("You are trying to check out with a future time. Please enter a valid time.")
            return
        }

        if (empToBeCheckedOut != null) {
            val checkinTime = empToBeCheckedOut.checkinDateTime
            val duration = java.time.Duration.between(checkinTime, checkoutTime)
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

    private fun getActiveCheckIn(id: String): Attendance? {
        return checkInAttendanceList.find { it.id == id && it.checkoutDateTime == null }
    }

    private fun findEmployeeInEmpList(id: String): Employee? {
        return employeeDatabase.find { it.id == id }
    }

    private fun addNewCheckin(emp: Employee, currentTime: LocalDateTime) {
        val empAttendance = Attendance(emp.id, currentTime)
        checkInAttendanceList.add(empAttendance)
        println("Employee ${emp.firstName} ${emp.lastName} checked in at $currentTime.")
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

fun main() {
    val employeeManager = EmployeeManager()
    val attendanceManager = AttendanceManager()

    employeeManager.createEmp("Bruce", "Wayne", "Manager")
    employeeManager.createEmp("Subash", "Smith", "CEO")
    employeeManager.createEmp("Tony", "Stark", "Developer")
    employeeManager.createEmp("Charles", "Xavier", "Developer")

    while (true) {
        println("\nEnter \n 1 -> checkin \n 2 -> checkout \n 3 -> exit")
        val action = readLine()?.trim() ?: ""

        if (action == "3") break
        if (action != "1" && action != "2") {
            println("Invalid action. Please enter '1', '2', or '3'.")
            continue
        }

        print("Enter ID: ")
        val id = readLine()?.trim() ?: ""

        val emp = employeeDatabase.find { it.id == id }
        if (emp == null) {
            println("No employee found with ID $id")
            continue
        }

        when (action) {
            "1" -> {
                println("Enter check-in date (yyyy-MM-dd) or press Enter to use current time: ")
                val checkinTime = getDateTime()
                attendanceManager.dailyCheckIn(emp, id, checkinTime)
            }
            "2" -> {
                println("Enter checkout date (yyyy-MM-dd) or press Enter to use current time: ")
                val checkoutTime = getDateTime()
                attendanceManager.dailyCheckOut(id, checkoutTime)
            }
        }
    }

    attendanceManager.showAttendance()
}
