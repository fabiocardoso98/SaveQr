package ipvc.estg.saveqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.Users
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registar)

        val add: ImageView = findViewById(R.id.add)
        val out: ImageView = findViewById(R.id.out)
        var txt_email: EditText = findViewById(R.id.email)
        var txt_name: EditText = findViewById(R.id.nome)
        var txt_username: EditText = findViewById(R.id.username)
        var txt_pwd: EditText = findViewById(R.id.password)
        var txt_confirm_pwd: EditText = findViewById(R.id.editText13)
        var txt_tlm: EditText = findViewById(R.id.tlm)

        out.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        add.setOnClickListener {

            if (txt_email.text.isNullOrEmpty() || txt_name.text.isNullOrEmpty() || txt_username.text.isNullOrEmpty() ||
                txt_pwd.text.isNullOrEmpty() || txt_confirm_pwd.text.isNullOrEmpty() || txt_tlm.text.isNullOrEmpty()
            ) {
                if (txt_email.text.isNullOrEmpty()) {
                    txt_email.error = "Empty email"
                }
                if (txt_name.text.isNullOrEmpty()) {
                    txt_name.error = "Empty name"
                }
                if (txt_username.text.isNullOrEmpty()) {
                    txt_username.error = "Empty username"
                }
                if (txt_pwd.text.isNullOrEmpty()) {
                    txt_pwd.error = "Empty password"
                }
                if (txt_confirm_pwd.text.isNullOrEmpty()) {
                    txt_confirm_pwd.error = "Empty repassword"
                }
                if (txt_tlm.text.isNullOrEmpty()) {
                    txt_tlm.error = "Empty phone"
                }
            } else if (txt_pwd.text.toString() != txt_confirm_pwd.text.toString()) {
                txt_confirm_pwd.error = "Don't match"
                txt_pwd.error = "Don't match"
            } else {
                val intent = Intent(this@RegistarActivity, LoginActivity::class.java)
                val request = ServiceBuilder.buildService(usersEndpoint::class.java)
                val call = request.postUser(
                    txt_name.text.toString(),
                    txt_username.text.toString(),
                    txt_email.text.toString(),
                    txt_pwd.text.toString()

                )
                call.enqueue(object : Callback<Users> {
                    override fun onResponse(
                        call: Call<Users>,
                        response: Response<Users>
                    ) {


                        if (response.isSuccessful) {
                            // Log.d("***","Sucesso")
                            Toast.makeText(this@RegistarActivity, "Sucesso!", Toast.LENGTH_SHORT)
                                .show();
                            startActivity(intent)
                            finish()
                            // communicator.passDataconn(root.nome.text.toString())
                            //  communicator.passDataconn(id,root.nome.text.toString(),root.username.text.toString(),root.email.text.toString(),root.password.text.toString())
                            // getActivity()?.getSupportFragmentManager()?.beginTransaction().remove(this@RegistarFragment).commit();


                        } else {
                            Log.d("***", "Falhou")

                        }

                    }

                    override fun onFailure(call: Call<Users>, t: Throwable) {

                    }

                })
            }

        }
    }
}