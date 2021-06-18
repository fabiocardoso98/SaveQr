package ipvc.estg.saveqr.ui.listapasta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R
import kotlinx.android.synthetic.main.fragment_listapasta.*
import kotlinx.android.synthetic.main.fragment_listapasta.view.*
import kotlinx.android.synthetic.main.popup_addpasta.view.*

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
        textView4.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_listaQrFragment, null))
        root.add.setOnClickListener {

            val mDialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.popup_addpasta, null)
            val mBuilder = AlertDialog.Builder(requireActivity()).setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            mDialogView.addpasta.setOnClickListener {
                val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")      //data
                val usersDesiredTimeZone: ZoneId = ZoneId.of("Europe/Lisbon")           //fuso Horario
                val now: ZonedDateTime = ZonedDateTime.now(usersDesiredTimeZone)
                val text: String = now.format(formatter)
                val nomepasta = mDialogView.email.text.toString()

                val pasta = mDialogView.email.text.toString()
                Toast.makeText(activity, "Sucesso! - "+pasta, Toast.LENGTH_SHORT).show();
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
