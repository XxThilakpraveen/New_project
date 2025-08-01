class EmployeeClass : EmployeeList() {

    private var incrementedId = 0

    fun createEmp(firstName: String, lastName: String, role: String): DataEmployee? {
        val id = generateUniqueId(firstName, lastName)
        if (getEmployee(id) != null) {
            println("Employee with ID $id already exists.")
            return null
        }

        val emp = DataEmployee(id, firstName, lastName, role)
        addEmployee(emp)
        return emp
    }

    private fun generateUniqueId(firstName: String, lastName: String): String {
        incrementedId++
        val prefix = "${firstName.firstOrNull()?.uppercaseChar() ?: 'X'}${lastName.lastOrNull()?.uppercaseChar() ?: 'Z'}"
        return "$prefix${incrementedId.toString().padStart(3, '0')}"
    }

    fun getValidEmployeeIdOrNull(): DataEmployee? {
        println("Enter Employee ID:")
        val inputId = readLine()?.trim()
        if (inputId.isNullOrEmpty()) {
            println("No ID entered.")
            return null
        }
        return getEmployee(inputId).also {
            if (it == null) println("Employee ID not found.")
        }
    }

    override fun toString(): String {
        val all = getAllEmployees()
        return if (all.isEmpty()) "No employees found."
        else all.joinToString("\n") {
            "ID: ${it.id} | Name: ${it.firstName} ${it.lastName} | Role: ${it.role}"
        }
    }
}
