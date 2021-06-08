package ipvc.estg.saveqr.ui.registar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ipvc.estg.saveqr.R

class RegistarFragment : Fragment() {

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
        registarViewModel.text.observe(viewLifecycleOwner, Observer {
         //   textView.text = it
        })
        return root
    }
}
