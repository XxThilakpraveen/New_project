import java.time.format.DateTimeFormatter

val displayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

fun main() {
    val empManager = EmployeeClass()
    val attManager = AttendanceClass()
    val menuHandler = MenuHandler(empManager, attManager)

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
            "1" -> menuHandler.handleCheckIn()
            "2" -> menuHandler.handleCheckOut()
            "3" -> println(attManager)
            "4" -> menuHandler.showActiveList()
            "5" -> println(empManager)
            "6" -> menuHandler.handleSummary()
            "7" -> {
                println("Exiting...")
                return
            }
            else -> println("Invalid input. Try again.")
        }
    }
}