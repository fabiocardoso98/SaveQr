package ipvc.estg.saveqr.ui.listaqr

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.ui.listapasta.ListaPastaViewModel


class ListaQrFragment : Fragment() {


    private lateinit var ListaQRviewModel: ListaQRViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ListaQRviewModel= ViewModelProviders.of(this).get(ListaQRViewModel::class.java)
        val root = inflater.inflate(R.layout.lista_q_r_fragment, container, false)
        val add: ImageView = root.findViewById(R.id.add)
        val textView4: TextView = root.findViewById(R.id.textView4)
        ListaQRviewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it

        })
        add.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_addQrUpdate, null))
        textView4.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_detalhesQR, null))


        return root
    }

}
