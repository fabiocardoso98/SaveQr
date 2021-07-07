package ipvc.estg.saveqr.ui.detalhesQR

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import ipvc.estg.saveqr.R

class DetalhesQR : Fragment() {

    companion object {
        fun newInstance() = DetalhesQR()
    }

    private lateinit var DetalhesQRviewModel: DetalhesQRViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DetalhesQRviewModel= ViewModelProviders.of(this).get(DetalhesQRViewModel::class.java)
        val root = inflater.inflate(R.layout.detalhes_q_r_fragment, container, false)
        DetalhesQRviewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it

        })


        return root
    }

}
