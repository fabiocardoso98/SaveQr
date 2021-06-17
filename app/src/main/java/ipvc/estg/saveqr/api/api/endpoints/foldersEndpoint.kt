package ipvc.estg.saveqr.api.api.endpoints

import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.api.models.UsersReturn
import retrofit2.Call
import retrofit2.http.GET
interface foldersEndpoint {

    @GET("/folders")
    fun getUsers(): Call<FoldersReturn>

}