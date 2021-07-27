package ipvc.estg.saveqr.api.api.endpoints

import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.models.QrCodeReturn
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

    @GET("/qrcodes/{Id}")
    fun getQrCodeById(@Path("Id") Id: Int?): Call<QrCodeReturn>

    @FormUrlEncoded
    @POST("/qrcodes")
    fun postQrCode(
        @Field("name") name: String?,
        @Field("content") content: String?,
        @Field("categoryId") categoryId: Int?,
        @Field("userId") userId: Int?,
    ): Call<QrCodeReturn>


    @DELETE("/qrcodes/{id}")
    fun deleteQRcode(@Path("id") id: Int): Call<Qrcodes>

}