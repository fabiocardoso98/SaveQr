package ipvc.estg.saveqr

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.Users
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var txt_email: EditText
        var txt_pwd: EditText

        val loginShared: SharedPreferences =
            getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
        val idLogin = loginShared.getInt(getString(R.string.idLogin), 0)
        val userLogin = loginShared.getString(getString(R.string.userLogin), "")

        txt_email = findViewById(R.id.email)
        txt_pwd = findViewById(R.id.password)


        if ((!userLogin.equals("")) && (idLogin != 0)) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }


        registar.setOnClickListener {
            val intent = Intent(this, RegistarActivity::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            val email: String = findViewById<EditText>(R.id.email).text.toString()
            val password: String = findViewById<EditText>(R.id.password).text.toString()
            val check: Boolean = findViewById<CheckBox>(R.id.guardar).isChecked

            if (txt_email.text.isNullOrEmpty() ||
                txt_pwd.text.isNullOrEmpty() ) {
                if (txt_email.text.isNullOrEmpty()) {
                    txt_email.error = "Empty email"
                }
                if (txt_pwd.text.isNullOrEmpty()) {
                    txt_pwd.error = "Empty name"
                }
            } else {


                val request = ServiceBuilder.buildService(usersEndpoint::class.java)
                val call = request.LogUser(email, password)

                call.enqueue(object : Callback<Users> {
                    override fun onResponse(call: Call<Users>, response: Response<Users>) {
                        if (response.isSuccessful) {
                            val intent = Intent(this@LoginActivity, SplashScreen::class.java)
                            Toast.makeText(
                                this@LoginActivity,
                                email,
                                Toast.LENGTH_SHORT
                            ).show()
                            loginShared.edit().putInt(
                                getString(R.string.idLogin),
                                response.body()?.id.toString().toInt()
                            ).commit()
                            loginShared.edit().putString(
                                getString(R.string.userLogin),
                                response.body()?.username.toString()
                            ).commit()

                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Erro, tente mais tarde!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                    override fun onFailure(call: Call<Users>, t: Throwable) {
                        Log.d("INTERNET LOGIN", t.toString())
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