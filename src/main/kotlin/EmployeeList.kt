class EmployeeList {
    val list = ArrayList<Employee>()

    fun add(emp: Employee): Boolean {
        if (!emp.isValid()) return false
        if (list.any { it.id == emp.id }) {
            println("Error: Employee with ID ${emp.id} already exists.")
            return false
        }
        return list.add(emp)
    }

    fun update(emp: Employee): Boolean {
        if (!emp.isValid()) return false
        val index = list.indexOfFirst { it.id == emp.id }
        if (index != -1) {
            list[index] = emp
            return  true
        } else return false
    }

    fun delete(id: String): Boolean {
        return list.removeIf { it.id == id }
    }
    override fun toString(): String {
        if (list.isEmpty()) return "No employees found."
        return list.joinToString("\n") { it.toString() }
    }
}
