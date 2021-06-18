package ipvc.estg.saveqr.api.api.endpoints

import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.api.models.UsersReturn
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface foldersEndpoint {

    @GET("/folders")
    fun getFolders(): Call<FoldersReturn>


    @FormUrlEncoded
    @POST("/folders")
    fun postFolders(
        @Field("name") name: String?
    ): Call<Folders>
}