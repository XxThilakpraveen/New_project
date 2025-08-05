class EmployeeList : ArrayList<Employee>() {

    override fun add(emp: Employee): Boolean {
        if (!emp.isValid()) return false
        if (this.any { it.id == emp.id }) {
            println("Error: Employee with ID ${emp.id} already exists.")
            return false
        }
        return super.add(emp)
    }

//    fun update(emp: Employee): Boolean {
//        if (!emp.isValid()) return false
//        val index = this.indexOfFirst { it.id == emp.id }
//        if (index != -1) {
//            this[index] = emp
//            return true
//        }
//        return false
//    }
//
//    fun delete(id: String): Boolean {
//        return this.removeIf { it.id == id }
//    }

    override fun toString(): String {
        if (this.isEmpty()) return "No employees found."
        return this.joinToString("\n") { it.toString() }
    }
}
