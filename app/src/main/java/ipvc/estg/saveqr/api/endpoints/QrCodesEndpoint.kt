package ipvc.estg.saveqr.api.endpoints

import ipvc.estg.saveqr.api.models.QrCodesRegisterReturn
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface QrCodesEndpoint {
    @GET("/qrcodes")
    fun getQrcodes(): Call<QrCodesReturn>

    @GET("/qrcodes/user")
    fun getQrcodesUser(): Call<QrCodesReturn>

    @FormUrlEncoded
    @POST("/qrcodes")
    fun postQrCode(
        @Field("name") name: String?,
        @Field("content") content: String?,
        @Field("categoryId") categoryId: Int?,
        @Field("userId") userId: Int?
    ): Call<Qrcodes>
}