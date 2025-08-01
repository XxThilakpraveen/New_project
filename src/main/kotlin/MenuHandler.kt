import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MenuHandler(private val empManager: EmployeeClass, private val attManager: AttendanceClass) {

    private val displayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    init {
        empManager.createEmp("Bruce", "Wayne", "Manager")
        empManager.createEmp("Subash", "Smith", "CEO")
        empManager.createEmp("Tony", "Stark", "Developer")
        empManager.createEmp("Charles", "Xavier", "Developer")
    }

    fun handleCheckIn() {
        val emp = empManager.getValidEmployeeIdOrNull() ?: return
        println("Enter check-in datetime (yyyy-MM-dd HH:mm) or press Enter for now:")
        val date = getDateTime()
        attManager.dailyCheckIn(emp, date)
    }

    fun handleCheckOut() {
        val emp = empManager.getValidEmployeeIdOrNull() ?: return
        println("Enter check-out datetime (yyyy-MM-dd HH:mm) or press Enter for now:")
        val date = getDateTime()
        attManager.dailyCheckOut(emp.id, date)
    }

    fun handleSummary() {
        println("Enter FROM datetime (yyyy-MM-dd HH:mm):")
        val from = getDateTime()
        println("En ter TO datetime (yyyy-MM-dd HH:mm):")
        val to = getDateTime()
        attManager.showSummaryBetween(from, to, empManager.getAllEmployees())
    }
    fun  showActiveList() {

        attManager.showCheckedInEmployees(empManager.getAllEmployees())

    }

    private fun getDateTime(): LocalDateTime {
        val input = readLine()?.trim()
        return if (input.isNullOrEmpty()) {
            LocalDateTime.now()
        } else {
            try {
                LocalDateTime.parse(input, displayFormatter)
            } catch (e: Exception) {
                println(e)
                println("Invalid format. Try again:")
                getDateTime()
            }
        }
    }
}
