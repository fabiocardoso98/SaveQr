package ipvc.estg.saveqr.api.api.models


import ipvc.estg.saveqr.api.api.models.Folders
import java.util.*

data class Folders(
    val id: Int,
    val nome: String,
    val img: String,
    val cor: String,
    val partilhado: Boolean,
    val dataAdicionado: Date,
    val dataAtualizado: Date,
    val categoryId: Int,
    val userId: Int
)
data class FoldersQr(
    val folderId: Int,
    val qrcodeId: Int
)
data class FoldersReturn(
    val msg: String,
    val status: String,
    val data: List<Folders>,
    val error: String
)

data class FoldersRegisterReturn(
    val msg: String,
    val status: String,
    val data: Folders,
    val error: String
)