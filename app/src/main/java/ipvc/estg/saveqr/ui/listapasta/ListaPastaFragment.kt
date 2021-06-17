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

                val pasta = mDialogView.email.text.toString()
                Toast.makeText(activity, "Sucesso! - "+pasta, Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss()
            }
            mDialogView.outPop.setOnClickListener {
                Toast.makeText(activity, "Saiu!", Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss()
            }

        }

        return root
    }
}
