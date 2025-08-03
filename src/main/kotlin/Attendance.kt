import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Attendance(
    val id: String,
    val checkInDateTime: LocalDateTime,
    var checkOutDateTime: LocalDateTime? = null,
    var workingHours: String = ""
) {

    fun checkInValid(): Boolean {
        return checkOutDateTime == null
    }

    fun checkOutValid(checkout: LocalDateTime): Boolean {
        return  checkout.isAfter(checkInDateTime)
    }

    fun calculateWorkingHours(): Boolean {
        val checkOutDateTime = checkOutDateTime ?: return false
        if (!checkOutDateTime.isAfter(checkInDateTime)) return false

        val duration = Duration.between(checkInDateTime, checkOutDateTime)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        workingHours = "%02d:%02d:%02d".format(hours, minutes, seconds)
        return true
    }

    override fun toString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val checkInStr = checkInDateTime.format(formatter)
        val checkOutStr = checkOutDateTime?.format(formatter) ?: "Not Checked Out"
        return "ID: $id | Check-in: $checkInStr | Check-out: $checkOutStr | Hours: $workingHours"
    }
}

