package ipvc.estg.saveqr.api.models

data class Users(
    val id: Int,
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val ativeCount: Boolean,
    val token: String
)

data class UsersReturn(
    val msg: String,
    val status: String,
    val data: List<Users>,
    val error: String
)