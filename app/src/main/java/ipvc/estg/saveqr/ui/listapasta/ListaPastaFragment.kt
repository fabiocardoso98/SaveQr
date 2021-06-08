package ipvc.estg.saveqr.ui.listapasta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R

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


        return root
    }
}
