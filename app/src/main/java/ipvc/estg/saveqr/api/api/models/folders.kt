package ipvc.estg.saveqr.api.api.models


import java.util.*

data class Folders(
    val id: Int,
    val name: String,
    val img: String,
    val cor: String,
    val partilhado: Boolean,
    val dataAdicionado: Date,
    val dataAtualizado: Date,
    val categoryId: Int,
    val userId: Int
)
data class FoldersReturn(
    val msg: String,
    val status: String,
    val data: List<Folders>,
    val error: String
)