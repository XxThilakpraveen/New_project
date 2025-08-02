class EmployeeManager {
    private val employeeList = EmployeeList()
    private var counter = 1

    private fun generateId(firstName: String, lastName: String): String {
        val first = firstName.trim().firstOrNull()?.uppercaseChar() ?: 'X'
        val last = lastName.trim().lastOrNull()?.uppercaseChar() ?: 'Y'
        return "$first$last${String.format("%03d", counter++)}"
    }

    fun addEmployee(firstName: String, lastName: String, role: Role, dept: String, reportingTo: String?): Boolean {
        val id = generateId(firstName, lastName)
        val emp = Employee(id, firstName.trim(), lastName.trim(), role, dept.trim(), reportingTo?.trim())
        return employeeList.add(emp)
    }

    fun updateEmployee(id: String, firstName: String, lastName: String, role: Role, dept: String, reportingTo: String?): Boolean {
        val emp = Employee(id, firstName.trim(), lastName.trim(), role, dept.trim(), reportingTo?.trim())
        return employeeList.update(emp)
    }

    fun readEmployee(id: String): Employee? {
        return employeeList.list.find { it.id == id }
    }

    fun deleteEmployee(id: String): Boolean {
        return employeeList.delete(id)
    }
}
