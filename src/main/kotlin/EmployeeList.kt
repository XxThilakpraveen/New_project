open class EmployeeList {
    protected val employeeDatabase = mutableListOf<DataEmployee>()

    open fun addEmployee(emp: DataEmployee): Boolean {
        employeeDatabase.add(emp)
        return true
    }

    fun getEmployee(id: String): DataEmployee? = employeeDatabase.find { it.id == id }

    fun getAllEmployees(): List<DataEmployee> = employeeDatabase
}
