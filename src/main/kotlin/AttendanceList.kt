import java.time.LocalDateTime

class AttendanceList {
    val list = ArrayList<Attendance>()

    fun add(att: Attendance): Boolean {
        if (!att.checkInValid()) return false
        if (list.any { it.id == att.id && it.checkOutDateTime == null }) {
            println("Error: Open attendance already exists for ID ${att.id}")
            return false
        }
        return list.add(att)
    }

    fun update(att: Attendance, checkout: LocalDateTime): Boolean {
        val index = list.indexOfFirst { it.id == att.id && it.checkOutDateTime == null }
        if (!att.checkOutValid(checkout)) {
            println("Checkout time cannot be before checkin time")
            return false
        }
        att.checkOutDateTime = checkout
        return if (index != -1) {
            att.calculateWorkingHours()
            list[index] = att
            true
        } else {
            println("No open attendance to update for ID ${att.id}")
            false
        }
    }

    fun delete(id: String, checkInDateTime: LocalDateTime): Boolean {
        return list.removeIf { it.id == id && it.checkInDateTime == checkInDateTime }
    }

    fun getActiveAttendances(): List<Attendance> {
        return list.filter { it.checkOutDateTime == null }
    }

    fun getAttendancesBetween(from: LocalDateTime, to: LocalDateTime): List<Attendance> {
        return list.filter { it.checkInDateTime.isAfter(from) && it.checkInDateTime.isBefore(to) }
    }

    override fun toString(): String {
        if (list.isEmpty()) return "No attendance records found."
        return list.joinToString("\n") { it.toString() }
    }
}