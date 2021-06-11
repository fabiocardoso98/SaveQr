package ipvc.estg.saveqr.api.endpoints

import ipvc.estg.saveqr.api.models.Users
import ipvc.estg.saveqr.api.models.UsersReturn
import retrofit2.Call
import retrofit2.http.GET

interface usersEndpoint {

    @GET("/user")
    fun getUsers(): Call<UsersReturn>

}