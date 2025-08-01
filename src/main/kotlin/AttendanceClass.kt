import java.time.Duration
import java.time.LocalDateTime

class AttendanceClass : AttendanceList() {

    fun dailyCheckIn(emp: DataEmployee, date: LocalDateTime = LocalDateTime.now()): Boolean {
        if (getOpenAttendance(emp.id) != null) {
            println("Already checked in.")
            return false
        }

        val newRecord = DataAttendance(emp.id, date)
        addAttendance(newRecord)
        println("Check-in successful: $newRecord")
        return true
    }

    fun dailyCheckOut(id: String, checkout: LocalDateTime) {
        val record = getOpenAttendance(id)
        if (record == null) {
            println("No active check-in found.")
            return
        }

        if (checkout == record.checkInDateTime) {
            println("Check-in and check-out times are the same. Entry discarded.")
            removeAttendance(record)
            return
        }

        if (checkout.isBefore(record.checkInDateTime)) {
            println("Invalid: checkout before check-in.")
            return
        }

        val duration = Duration.between(record.checkInDateTime, checkout)
        if (duration.toHours() > 24) {
            println("Checkout exceeds 24 hours — invalid.")
            return
        }

        record.checkOutDateTime = checkout
        record.workingHours = String.format(
            "%02d:%02d:%02d",
            duration.toHours(),
            duration.toMinutesPart(),
            duration.toSecondsPart()
        )

        updateAttendance(record)
        println("Checked out: $record")
    }

    fun showSummaryBetween(from: LocalDateTime, to: LocalDateTime, employees: List<DataEmployee>) {
        val records = getAttendanceInRange(from, to)
        if (records.isEmpty()) {
            println("No records found in this range.")
            return
        }

        println("Summary between $from and $to:")
        records.forEach { record ->
            val name = employees.find { it.id == record.id }?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown"
            println("ID: ${record.id}, Name: $name, Worked: ${record.workingHours}")
        }
    }

    fun showCheckedInEmployees(employees: List<DataEmployee>) {
        val checkedIn = getCurrentlyCheckedIn()
        if (checkedIn.isEmpty()) {
            println("No employees are currently checked in.")
            return
        }

        println("Currently checked-in employees:")
        checkedIn.forEach { record ->
            val name = employees.find { it.id == record.id }?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown"
            val formattedCheckIn = record.checkInDateTime.format(displayFormatter)
            println("ID: ${record.id}, Name: $name, Check-in: $formattedCheckIn")
        }
    }


    override fun toString(): String {
        return if (getAll().isEmpty()) {
            "No attendance records found."
        } else {
            getAll().joinToString("\n")
        }
    }
}
