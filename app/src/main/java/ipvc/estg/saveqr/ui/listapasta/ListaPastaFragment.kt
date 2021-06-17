package ipvc.estg.saveqr.ui.listapasta

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.foldersEndpoint
import ipvc.estg.saveqr.api.api.models.Folders
import kotlinx.android.synthetic.main.fragment_listapasta.view.*
import kotlinx.android.synthetic.main.popup_addpasta.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ListaPastaFragment : Fragment() {

    private lateinit var listaPastaViewModel: ListaPastaViewModel
    var idUser: Any? = null;
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       listaPastaViewModel =
                ViewModelProviders.of(this).get(ListaPastaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_listapasta, container, false)
        val textView4: TextView = root.findViewById(R.id.textView4)
        listaPastaViewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it


        })

        textView4.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.nav_listaQrFragment,
                null
            )
        )


        val loginShared: SharedPreferences? = activity?.getSharedPreferences(getString(R.string.login_p), Context.MODE_PRIVATE)
        val idUser = loginShared?.getInt(getString(R.string.idLogin), 0)



        root.add.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(requireActivity()).inflate(R.layout.popup_addpasta, null)
            val mBuilder = AlertDialog.Builder(requireActivity()).setView(mDialogView)
            val mAlertDialog = mBuilder.show()


            mDialogView.addpasta.setOnClickListener {
                val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")      //data
                val usersDesiredTimeZone: ZoneId = ZoneId.of("Europe/Lisbon")           //fuso Horario
                val now: ZonedDateTime = ZonedDateTime.now(usersDesiredTimeZone)
                val text: String = now.format(formatter)
                val nomepasta = mDialogView.email.text.toString()

                if (nomepasta.isNullOrEmpty()) {
                    Toast.makeText(activity, "Sem nome", Toast.LENGTH_SHORT).show()
                }else {Toast.makeText(activity, "Pasta " + nomepasta + " Criada", Toast.LENGTH_SHORT).show()
                mAlertDialog.dismiss()
                val request = ServiceBuilder.buildService(foldersEndpoint::class.java)
                val call = request.postFolders(
                    nomepasta,
                    text,
                    idUser as Int?
                )
                call.enqueue(object : Callback<Folders> {
                    override fun onResponse(
                        call: Call<Folders>,
                        response: Response<Folders>
                    ) {


                        if (response.isSuccessful) {
                            Log.d("***", "Sucesso")

                            // communicator.passDataconn(root.nome.text.toString())
                            //  communicator.passDataconn(id,root.nome.text.toString(),root.username.text.toString(),root.email.text.toString(),root.password.text.toString())
                            // getActivity()?.getSupportFragmentManager()?.beginTransaction().remove(this@RegistarFragment).commit();


                        } else {
                            Log.d("***", "Falhou")

                        }

                    }

                    override fun onFailure(call: Call<Folders>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                }
                )}
            }
        }

        return root
    }
}
