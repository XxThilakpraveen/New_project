class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: Role,
    val department: String,
    val reportingTo: String?
) {
    fun isValid(): Boolean {
        val valid = firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                department.isNotBlank() &&
                ((role == Role.CEO && reportingTo == null) ||
                        (role != Role.CEO && !reportingTo.isNullOrBlank()))

        if (!valid) {
            println("Validation failed for employee: $firstName $lastName (Role: $role)")
        }

        return valid
    }


    override fun toString(): String {
        return "ID: $id, Name: $firstName $lastName, Role: $role, Dept: $department, ReportsTo: ${reportingTo ?: "None"}"
    }
}
