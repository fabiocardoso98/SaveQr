package ipvc.estg.saveqr

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.Users
import ipvc.estg.saveqr.api.models.UsersRegisterReturn
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginShared: SharedPreferences = getSharedPreferences(
            getString(R.string.login_p), Context.MODE_PRIVATE
        )
        val idLogin = loginShared.getInt(getString(R.string.idLogin), 0)
        val userLogin = loginShared.getString(getString(R.string.userLogin), "")
        val status = loginShared.getBoolean(getString(R.string.status),true)

        if ((!userLogin.equals("")) && (idLogin != 0) && status) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }




        registar.setOnClickListener {
            val intent = Intent(this, RegistarActivity::class.java)
            startActivity(intent)
            finish()
        }

        add.setOnClickListener {

            var username = findViewById<EditText>(R.id.email).text.toString()
            var password = findViewById<EditText>(R.id.password).text.toString()
            val intent = Intent(this, MainActivity::class.java)
            val check: Boolean = findViewById<CheckBox>(R.id.guardar).isChecked

            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {

                if (username.isNullOrEmpty()) {
                    Toast.makeText(this@LoginActivity, "Username empty!", Toast.LENGTH_SHORT).show()
                }
                if (password.isNullOrEmpty()) {
                    Toast.makeText(this@LoginActivity, "Password empty!", Toast.LENGTH_SHORT).show()
                }
            } else {

                val request = ServiceBuilder.buildService(usersEndpoint::class.java)
                val call = request.LogUser(username, password)

                call.enqueue(object : Callback<UsersRegisterReturn> {
                    override fun onResponse(
                        call: Call<UsersRegisterReturn>,
                        response: Response<UsersRegisterReturn>
                    ) {
                        if (response.body()?.status=="success") {
                            if(check){
                                loginShared.edit().putInt(getString(R.string.idLogin), response.body()?.data!!.id).commit()
                                loginShared.edit().putString(getString(R.string.userLogin), response.body()?.data!!.username).commit()
                                loginShared.edit().putBoolean(getString(R.string.status), true).commit()
                            }
                            else{
                                loginShared.edit().putInt(getString(R.string.idLogin), response.body()?.data!!.id).commit()
                                loginShared.edit().putString(getString(R.string.userLogin), response.body()?.data!!.username).commit()
                                loginShared.edit().putBoolean(getString(R.string.status), false).commit()
                            }
                            startActivity(intent)


                        }
                    }

                    override fun onFailure(call: Call<UsersRegisterReturn>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Erro, tente mais tarde!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            }

        }


    }



}

