//import java.time.LocalDateTime
//
//open class AttendanceList {
//    protected val attendanceDatabase = mutableListOf<DataAttendance>()
//
//    fun addAttendance(attendance: DataAttendance) {
//        attendanceDatabase.add(attendance)
//    }
//
//    fun updateAttendance(updated: DataAttendance) {
//        val index = attendanceDatabase.indexOfFirst {
//            it.id == updated.id && it.checkInDateTime == updated.checkInDateTime
//        }
//        if (index != -1) {
//            attendanceDatabase[index] = updated
//        } else {
//            println("Update failed: No matching record found.")
//        }
//    }
//
//    fun removeAttendance(attendance: DataAttendance) {
//        val removed = attendanceDatabase.removeIf {
//            it.id == attendance.id && it.checkInDateTime == attendance.checkInDateTime
//        }
//        if (!removed) println("Delete failed: No matching attendance found.")
//    }
//
//    fun getOpenAttendance(id: String): DataAttendance? {
//        return attendanceDatabase.find { it.id == id && it.checkOutDateTime == null }
//    }
//
//    fun getCurrentlyCheckedIn(): List<DataAttendance> {
//        return attendanceDatabase.filter { it.checkOutDateTime == null }
//    }
//
//    fun getAttendanceInRange(from: LocalDateTime, to: LocalDateTime): List<DataAttendance> {
//        return attendanceDatabase.filter {
//            it.checkInDateTime.isAfter(from.minusSeconds(1)) &&
//                    it.checkOutDateTime?.isBefore(to.plusSeconds(1)) == true
//        }
//    }
//
//    fun getAll(): List<DataAttendance> = attendanceDatabase
//}
