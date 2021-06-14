package ipvc.estg.saveqr.ui.registar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.Communicator
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.UsersReturn
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_registar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class RegistarFragment : Fragment() {

    private lateinit var txt_name: EditText
    private lateinit var txt_username: EditText
    private lateinit var txt_email: EditText
    private lateinit var txt_pwd: EditText
    private lateinit var txt_confirm_pwd: EditText
    private lateinit var txt_tlm: EditText
    private lateinit var communicator: Communicator


    private lateinit var registarViewModel: RegistarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registarViewModel =
            ViewModelProviders.of(this).get(RegistarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_registar, container, false)
        // val textView: TextView = root.findViewById(R.id.text_gallery)
        val add: ImageView = root.findViewById(R.id.add)
        val out: ImageView = root.findViewById(R.id.out)
        txt_email = root.findViewById(R.id.email)
        txt_name = root.findViewById(R.id.nome)
        txt_username = root.findViewById(R.id.username)
        txt_pwd = root.findViewById(R.id.password)
        txt_confirm_pwd = root.findViewById(R.id.editText13)
        txt_tlm = root.findViewById(R.id.tlm)
        registarViewModel.text.observe(viewLifecycleOwner, Observer {
            //   textView.text = it

        })


        // communicator = activity as Communicator


        root.add.setOnClickListener {

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

                val request = ServiceBuilder.buildService(usersEndpoint::class.java)
                val call = request.postUser(
                    txt_name.text.toString(),
                    txt_username.text.toString(),
                    txt_email.text.toString(),
                    txt_pwd.text.toString()

                )
                call.enqueue(object : Callback<UsersReturn> {
                    override fun onResponse(
                        call: Call<UsersReturn>,
                        response: Response<UsersReturn>
                    ) {


                        if (response.isSuccessful) {
                            // Log.d("***","Sucesso")
                            Toast.makeText(activity, "Sucesso!", Toast.LENGTH_SHORT).show();
                            // communicator.passDataconn(id,root.nome.text.toString(),root.username.text.toString(),root.email.text.toString(),root.password.text.toString())
                          //  getActivity()?.getSupportFragmentManager()?.beginTransaction().remove(this@RegistarFragment).commit();


                        } else {
                            Log.d("***", "Falhou")

                        }

                    }

                    override fun onFailure(call: Call<UsersReturn>, t: Throwable) {

                    }

                })
                Toast.makeText(activity, "Sucesso!", Toast.LENGTH_SHORT).show();
              //  Navigation.createNavigateOnClickListener(R.id.nav_home, null)
                Navigation.findNavController(requireView()).navigate(R.id.nav_listapasta);
                //parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        }

        out.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_home, null))



        return root
    }


}


