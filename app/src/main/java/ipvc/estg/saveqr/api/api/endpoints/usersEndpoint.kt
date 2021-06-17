package ipvc.estg.saveqr.api.endpoints

import ipvc.estg.saveqr.api.models.Users
import ipvc.estg.saveqr.api.models.UsersRegisterReturn
import ipvc.estg.saveqr.api.models.UsersReturn
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface usersEndpoint {

    @GET("/user")
    fun getUsers(): Call<UsersReturn>

    @FormUrlEncoded
    @POST("/user/login")
    fun LogUser( @Field("username") username: String?,
                 @Field("password") password: String?
    ): Call<UsersRegisterReturn>

    @FormUrlEncoded
    @POST("/user")
    fun postUser(
        @Field("name") name: String?,
        @Field("username") username: String?,
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<Users>

}