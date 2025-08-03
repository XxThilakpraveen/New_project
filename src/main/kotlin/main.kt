import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val displayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

fun main() {
    val employeeManager = EmployeeManager()
//    val employee        = Employee("JD001","John", "Doe", Role.DEVELOPER, "Engineering", "AS002")

    fun readEmployeeId(): String? {
        print("Enter Employee ID: ")
        return readLine()?.trim()
    }

    fun readDateTime(prompt: String): LocalDateTime? {
        print("$prompt (yyyy-MM-dd HH:mm): ")
        val input = readLine()?.trim()
        if (input == "") return LocalDateTime.now()
        return try {
            LocalDateTime.parse(input, displayFormatter)
        } catch (e: Exception) {
            println("Invalid datetime format. Please use yyyy-MM-dd HH:mm.")
            null
        }
    }

    fun handleCheckIn() {
        val id = readEmployeeId() ?: return
        val checkInTime = readDateTime("Enter Check-in DateTime") ?: return
        if (employeeManager.checkIn(id, checkInTime)) println("Check-in successful.")
    }

    fun handleCheckOut() {
        val id = readEmployeeId() ?: return
        val checkOutTime = readDateTime("Enter Check-out DateTime") ?: return
        if (employeeManager.checkOut(id, checkOutTime)) println("Check-out successful.")
    }

    fun showActiveList() {
        println("--- Active Attendance List ---")
        println(employeeManager.listActiveAttendances())
    }

    fun handleSummary() {
        val fromDate = readDateTime("Enter From DateTime") ?: return
        val toDate = readDateTime("Enter To DateTime") ?: return
        println("--- Attendance Summary ---")
        println(employeeManager.listAttendancesBetween(fromDate, toDate))
    }

    while (true) {
        println(
            """
                |Enter:
                | 1 -> Check-in
                | 2 -> Check-out
                | 3 -> Show Attendance
                | 4 -> Show Active Attendance
                | 5 -> Show Employee List
                | 6 -> Summary by Range
                | 7 -> Exit
                """.trimMargin()
        )

        when (readLine()?.trim()) {
            "1" -> handleCheckIn()
            "2" -> handleCheckOut()
            "3" -> println(employeeManager.printAttendance())
            "4" -> showActiveList()
            "5" -> println(employeeManager.printEmployee())
            "6" -> handleSummary()
            "7" -> {
                println("Exiting...")
                return
            }
            else -> println("Invalid input. Try again.")
        }
    }
}
