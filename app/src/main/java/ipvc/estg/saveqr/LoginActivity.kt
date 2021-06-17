package ipvc.estg.saveqr

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.UsersRegisterReturn
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


// Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        imageView2.setOnClickListener {

            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...)
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
        // updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
          //  Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        //    updateUI(null)
        }
    }







}

