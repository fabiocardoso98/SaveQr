package ipvc.estg.saveqr.api.models

class Categories (
    val id: Int,
    val name: String,
    val descricao: String
)

data class CategoriesReturn(
    val msg: String,
    val status: String,
    val data: List<Categories>,
    val error: String
)