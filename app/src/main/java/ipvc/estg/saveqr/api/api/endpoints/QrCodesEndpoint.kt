package ipvc.estg.saveqr.api.api.endpoints

import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.models.QrCodesRegisterReturn
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import retrofit2.Call
import retrofit2.http.*

interface QrCodesEndpoint {
    //este da
    @GET("/qrcodes")
    fun getQrcodes(): Call<QrCodesReturn>

    @GET("/qrcodes/folder/{folderId}")
    fun getQrCodeByFolder(@Path("folderId") folderId: Int?): Call<QrCodesReturn>

    @FormUrlEncoded
    @POST("/qrcodes")
    fun postQrCode(
        @Field("name") name: String?,
        @Field("content") content: String?,
        @Field("categoryId") categoryId: Int?,
        @Field("userId") userId: Int?,
        @Field("folderId") folderId: Int?
    ): Call<Qrcodes>


    @DELETE("/qrcodes/{id}")
    fun deleteQRcode(@Path("id") id: Int): Call<Qrcodes>

}