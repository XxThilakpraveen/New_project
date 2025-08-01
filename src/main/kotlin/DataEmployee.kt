data class DataEmployee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: String
) {
    override fun toString(): String {
        return "ID: $id, Name: $firstName $lastName, Role: $role"
    }
}