import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
    val checkinDateTime: String
)

class Main {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun createUser(firstName: String, lastName: String, role: String): EmpDetails {

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

    fun dailyCheckIn(emp: EmpDetails): Boolean {
        val currentTime = getCurrentTime()
        val existingEmp = findEmployee(emp.id)

        return if (existingEmp != null) {
            if (hasAlreadyCheckedIn(emp.id)) {
                println("Employee with ID ${emp.id} has already checked in.")
                false
            } else {
                addNewCheckin(emp, currentTime)
                true
            }
        } else {
            println("Employee with ID ${emp.id} does not exist in the system.")
            false
        }
    }

    private fun getCurrentTime(): String {
        return LocalDateTime.now().format(formatter)
    }

    private fun findEmployee(id: String): EmpDetails? {
        return employeeDatabase.find { it.id == id }
    }

    private fun hasAlreadyCheckedIn(id: String): Boolean {
        return checkinAttendanceList.any { it.id == id }
    }

    private fun addNewCheckin(emp: EmpDetails, currentTime: String) {
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

    val emp1 = system.createUser("Bruce", "Wayne", "Manager")
    val emp2 = system.createUser("Subash", "Smith", "CEO")
    val emp3 = system.createUser("Tony", "Stark", "Developer")
    val emp4 = system.createUser("Charles", "Xavier", "Developer")

    system.dailyCheckIn(emp1)
    system.dailyCheckIn(emp2)
    system.dailyCheckIn(emp3)
    system.dailyCheckIn(emp4)
    system.dailyCheckIn(emp4)

    system.showAttendance()

    system.reportTo(emp4)
    system.reportTo(emp1)
}
