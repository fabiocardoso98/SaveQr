package ipvc.estg.saveqr.api.models

import java.util.*

class Qrcode (
    val id: Int,
    val name: String,
    val conteudo: String,
    val mg: String,
    val atlng: String,
    val localizacao: String,
    val dataAdicionado: Date,
    val dataAtualizado: Date,
    val categories_id: Int
)

data class QrcodeReturn(
    val msg: String,
    val status: String,
    val data: List<Qrcode>,
    val error: String
)