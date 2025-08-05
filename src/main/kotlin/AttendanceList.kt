import java.time.LocalDateTime

class AttendanceList : ArrayList<Attendance>() {

    override fun add(att: Attendance): Boolean {
//        if (!att.checkInValid()) return false
        if (this.any { it.id == att.id && it.checkOutDateTime == null }) {
            println("Error: Open attendance already exists for ID ${att.id}")
            return false
        }
        return super.add(att)
    }

    fun update(att: Attendance, checkout: LocalDateTime): Boolean {
        val index = this.indexOfFirst { it.id == att.id && it.checkOutDateTime == null }
        if (checkout.isBefore(att.checkInDateTime)) {
            println("Checkout time cannot be before checkin time")
            return false
        }
        if (checkout.toLocalDate() == att.checkInDateTime.toLocalDate()) {
            println("Check-out must be on the same date as check-in.")
            return false
        }
        att.checkOutDateTime = checkout
        return if (index != -1) {
            att.calculateWorkingHours()
            this[index] = att
            true
        } else {
            println("No open attendance to update for ID ${att.id}")
            false
        }
    }

    fun delete(id: String, checkInDateTime: LocalDateTime): Boolean {
        return this.removeIf { it.id == id && it.checkInDateTime == checkInDateTime }
    }

    fun getActiveAttendances(): List<Attendance> {
        return this.filter { it.checkOutDateTime == null }
    }

    fun getAttendancesBetween(from: LocalDateTime, to: LocalDateTime): String {
        val attendanceBetween = this.filter {
            it.checkInDateTime.isAfter(from) && it.checkInDateTime.isBefore(to)
        }

        if (attendanceBetween.isEmpty()) {
            return "No attendance records found for the given time range."
        }

        val groupedById = attendanceBetween.groupBy { it.id }

        return groupedById.entries.joinToString("\n") { (id, records) ->
            val totalHours = records.sumOf { attendance ->
                val parts = attendance.workingHours.split(":")
                val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
                val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0
                val seconds = parts.getOrNull(2)?.toIntOrNull() ?: 0
                hours + (minutes / 60.0) + (seconds / 3600.0)
            }

            "ID: $id -> Total Working Hours: %.2f hrs".format(totalHours)
        }
    }

    override fun toString(): String {
        if (this.isEmpty()) return "No attendance records found."
        return this.joinToString("\n") { it.toString() }
    }
}
