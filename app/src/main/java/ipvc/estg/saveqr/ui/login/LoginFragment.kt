package ipvc.estg.saveqr.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.Users
import kotlinx.android.synthetic.main.fragment_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    val loginShared: SharedPreferences = this.activity.getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var txt_email: EditText
    private lateinit var txt_pwd: EditText


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val registar: TextView = root.findViewById(R.id.registar)
        val enter: ImageView = root.findViewById(R.id.add)
        txt_email = root.findViewById(R.id.email)
        txt_pwd = root.findViewById(R.id.password)
        val check: Boolean = root.findViewById<CheckBox>(R.id.guardar).isChecked
        //loginShared = root.findViewById(R.string.sharedLogin).isChecked

        loginViewModel.text.observe(viewLifecycleOwner, Observer {
          //  textView.text = it
        })




        root.add.setOnClickListener {
            val username: String = root.findViewById<EditText>(R.id.username).text.toString()
            val password: String = root.findViewById<EditText>(R.id.password).text.toString()
            val check: Boolean = root.findViewById<CheckBox>(R.id.guardar).isChecked

            if (txt_email.text.isNullOrEmpty() || txt_pwd.text.isNullOrEmpty())
             {
                if (txt_email.text.isNullOrEmpty()) {
                    txt_email.error = "Empty email"
                }
                if (txt_pwd.text.isNullOrEmpty()) {
                    txt_pwd.error = "Empty pass"
                }
            }
            else{


            val request = ServiceBuilder.buildService(usersEndpoint::class.java)
            val call = request.LogUser(username,password)

            call.enqueue(object : Callback<Users> {
                override fun onResponse(
                    call: Call<Users>,
                    response: Response<Users>
                ) {


                    if (response.isSuccessful) {

                        loginShared.edit().putInt(getString(R.string.idLogin), response.body()?.id.toString().toInt()).commit()
                        loginShared.edit().putString(getString(R.string.userLogin), response.body()?.username.toString()).commit()
                        Navigation.findNavController(requireView()).navigate(R.id.nav_listapasta);
                        Toast.makeText(activity, "Sucesso!", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(activity, "Falhou!", Toast.LENGTH_SHORT).show();

                    }

                }

                override fun onFailure(call: Call<Users>, t: Throwable) {

                }

            })

            }

        }


            registar.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_registar, null))



     /*   enter.setOnClickListener {
            val intent = Intent(this, fragment_registar::class.java)
            startActivity(intent)
        }*/
        return root

    }

}
