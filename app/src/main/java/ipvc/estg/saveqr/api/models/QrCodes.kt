package ipvc.estg.saveqr.api.models

data class Qrcodes(
    val id: Int,
    val name: String,
    val content: String,
    val categoryId: Int,
    val userId: Int
)

data class QrCodesReturn(
    val msg: String,
    val status: String,
    val data: List<Qrcodes>,
    val error: String
)
data class QrCodesRegisterReturn(
    val msg: String,
    val status: String,
    val data: Qrcodes,
    val error: String
)