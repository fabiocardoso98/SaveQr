package ipvc.estg.saveqr.api.api.endpoints

import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.api.models.UsersReturn
import retrofit2.Call
import retrofit2.http.*

interface foldersEndpoint {

    @GET("/folders")
    fun getFolders(): Call<FoldersReturn>

    @FormUrlEncoded
    @POST("/folders")
    fun postFolders(
        @Field("nome") nome: String?,
        @Field("dataAdicionado") dataAdicionado: String?,
        @Field("userId") userId: Int?

    ): Call<Folders>

    @DELETE("/folders/{id}/{userId}")
    fun deleteFolders(@Path("id") id: Int, @Path("userId") userId: Int): Call<Folders>

}