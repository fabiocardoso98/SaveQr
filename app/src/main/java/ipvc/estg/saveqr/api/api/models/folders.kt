package ipvc.estg.saveqr.api.api.models


import java.util.*

data class Folders(
    val id: Int,
    val nome: String,
    val dataAdicionado: Date,
    val userId:Int

)
data class FoldersReturn(
    val msg: String,
    val status: String,
    val data: List<Folders>,
    val error: String
)