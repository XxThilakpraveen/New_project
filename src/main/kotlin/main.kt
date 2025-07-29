import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException
import kotlin.random.Random

val checkinAttendanceList = mutableListOf<attendaceDetails>()
val employeeDatabase = mutableListOf<EmpDetails>()

data class EmpDetails(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: String
)

data class attendaceDetails(
    val id: String,
    val checkinDateTime: LocalDateTime,
    var checkoutDateTime: LocalDateTime? = null
)

class Main {

    fun createEmp(firstName: String, lastName: String, role: String): EmpDetails {

        val generatedId = generateUniqueId(firstName, lastName)
        val newEmp = EmpDetails(generatedId, firstName, lastName, role)
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

    fun dailyCheckIn(emp: EmpDetails, id: String, Date: LocalDateTime = LocalDateTime.now()): Boolean {
        val existingEmp = findEmployeeInEmpList(id)

        return if (existingEmp != null) {
            if (hasAlreadyCheckedIn(id)) {
                println("Employee with ID ${id} has already checked in.")
                false
            } else {
                addNewCheckin(emp, Date)
                true
            }
        } else {
            println("Employee with ID ${id} does not exist in the system.")
            false
        }
    }

    fun dailyCheckOut(id: String, checkoutTime: LocalDateTime = LocalDateTime.now()) {
        val record = checkinAttendanceList.find { it.id == id && it.checkoutDateTime == null }

        if (record != null) {
            record.checkoutDateTime = checkoutTime
            println("Employee with ID $id checked out at $checkoutTime.")
        } else {
            println("No active check-in found for ID $id.")
        }
    }


    private fun findEmployeeInEmpList(id: String): EmpDetails? {
        return employeeDatabase.find { it.id == id }
    }

    private fun findEmployeeInAttendanceList(id: String): attendaceDetails? {
        return checkinAttendanceList.find { it.id == id }
    }

    private fun hasAlreadyCheckedIn(id: String): Boolean {
        return checkinAttendanceList.any { it.id == id }
    }

    private fun addNewCheckin(emp: EmpDetails, currentTime: LocalDateTime) {
        val empAttendance = attendaceDetails(emp.id, currentTime)
        checkinAttendanceList.add(empAttendance)
        println("Employee ${emp.firstName} ${emp.lastName} checked in at $currentTime.")
    }
    fun reportTo(emp: EmpDetails) {
        val empId = emp.id
        val empRole = emp.role

        if (empRole == "Developer") {
            val managers = employeeDatabase.filter { it.role == "Manager" }

            if (managers.isNotEmpty()) {
                println("\nEmployee of ID $empId of role $empRole reports to the following manager(s):")
                managers.forEach { println("Manager ID: ${it.id}, Name: ${it.firstName} ${it.lastName}") }

            }
            else {
                println("\nManager not found")
            }
            }
            else if ( empRole == "Manager" ){
                val managers = employeeDatabase.filter { it.role == "CEO" }

                if (managers.isNotEmpty()) {
                    println("\nEmployee of ID $empId of role $empRole reports to CEO:")
                    managers.forEach { println("CEO ID: ${it.id}, Name: ${it.firstName} ${it.lastName}") }
                }
                else {
                    println("\nNo CEO found in the org")
                }
        }
        else {
            println("\nCEO reports to no one")
        }
    }
    fun showAttendance() {
        println("\n--- Attendance List ---")
        checkinAttendanceList.forEach {
            println("ID: ${it.id}, Time: ${it.checkinDateTime}")
        }
    }
}
fun main() {
    val system = Main()

    system.createEmp("Bruce", "Wayne", "Manager")
    system.createEmp("Subash", "Smith", "CEO")
    system.createEmp("Tony", "Stark", "Developer")
    system.createEmp("Charles", "Xavier", "Developer")

    fun getDateTime() : LocalDateTime{

        val dateInput = readLine()
        val  dateTime = try {
            if (dateInput.isNullOrBlank()) {
                LocalDateTime.now()
            } else {
                LocalDate.parse(dateInput).atTime(LocalTime.now())
            }
        } catch (e: DateTimeParseException) {
            println("Invalid format. Defaulting to current date and time.")
            LocalDateTime.now()
        }
        return dateTime

    }

    while (true) {
        println("\nEnter 'checkin' or 'checkout' or 'exit': ")
        val action = readLine()?.trim()?.lowercase() ?: ""

        if (action == "exit") break
        if (action != "checkin" && action != "checkout") {
            println("Invalid action. Please enter 'checkin', 'checkout' or 'exit'.")
            continue
        }

        println("Enter ID: ")
        val id = readLine()?.trim() ?: ""

        val emp = employeeDatabase.find { it.id == id }
        if (emp == null) {
            println("No employee found with ID $id")
            continue
        }

        when (action) {
            "checkin" -> {
                // Always ask for date (like your getEmpDetails)
                println("Enter check-in date (yyyy-MM-dd): ")
                val checkinTime = getDateTime()
                system.dailyCheckIn(emp, id, checkinTime)
            }

            "checkout" -> {
                // Optional date input
                println("Enter checkout date (yyyy-MM-dd) or press Enter to use current time: ")
                val checkoutTime = getDateTime()
                system.dailyCheckOut(id, checkoutTime)
            }
        }
    }


    system.showAttendance()

//    system.reportTo(emp4)
//    system.reportTo(emp1)
}