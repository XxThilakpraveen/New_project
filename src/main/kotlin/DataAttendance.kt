//import java.time.LocalDateTime
//
//data class DataAttendance(
//    val id: String,
//    val checkInDateTime: LocalDateTime,
//    var checkOutDateTime: LocalDateTime? = null,
//    var workingHours: String = ""
//) {
//    override fun toString(): String {
//        val checkIn = checkInDateTime.format(displayFormatter)
//        val checkOut = checkOutDateTime?.format(displayFormatter) ?: "Still Checked-in"
//        val durationDisplay = if (workingHours.isNotBlank()) ", Worked: $workingHours" else ""
//        return "ID: $id, Check-in: $checkIn, Check-out: $checkOut$durationDisplay"
//    }
//}