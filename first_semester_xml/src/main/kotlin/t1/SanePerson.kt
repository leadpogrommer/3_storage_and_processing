package t1

data class SanePerson(
    val id: String,
    val name: String,

    val childrenNumber: Int,
    val siblingsNumber: Int,

    val spouseId: String?,

    val fatherId: String?,
    val motherId: String?,

    val childrenIds: Set<String>,

    val siblingsIds: Set<String>,
)