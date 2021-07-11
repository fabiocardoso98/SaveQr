package ipvc.estg.saveqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
            }

        }
    }
}