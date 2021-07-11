package ipvc.estg.saveqr.api.api.endpoints

import ipvc.estg.saveqr.api.models.QrCodesRegisterReturn
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import retrofit2.Call
import retrofit2.http.*

interface QrCodesEndpoint {
    //este da
    @GET("/qrcodes")
    fun getQrcodes(): Call<QrCodesReturn>

    //este nao da
    @GET("/qrcodes/user/{userId}")
    fun getQrCodeByUser(@Path("userId") userId: Int?): Call<QrCodesReturn>

    @FormUrlEncoded
    @POST("/qrcodes")
    fun postQrCode(
        @Field("name") name: String?,
        @Field("content") content: String?,
        @Field("categoryId") categoryId: Int?,
        @Field("userId") userId: Int?
    ): Call<Qrcodes>
}