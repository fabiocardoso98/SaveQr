package ipvc.estg.saveqr.api.models

import java.util.*

class Folders(
    val id: Int,
    val name: String,
    val mg: String,
    val cor: String,
    val partilhado: Boolean,
    val dataAdicionado: Date,
    val dataAtualizado: Date,
    val users_id: Int,
    val categories_id: Int

)

data class FoldersReturn(
    val msg: String,
    val status: String,
    val data: List<Folders>,
    val error: String
)