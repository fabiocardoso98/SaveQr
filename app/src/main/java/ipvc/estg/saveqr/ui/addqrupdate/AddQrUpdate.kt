package ipvc.estg.saveqr.ui.addqrupdate

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.ui.lerQr.LerQrActivity


class AddQrUpdate : Fragment() {
    companion object {
        fun newInstance() = AddQrUpdate()
    }

    private lateinit var viewModel: AddQrUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.add_qr_update_fragment, container, false)
        val addqr: ImageView = root.findViewById(R.id.plus)
        addqr.setOnClickListener {
            val intent = Intent(requireContext(), LerQrActivity::class.java)
            intent.putExtra("EXTRA", "")
            startActivity(intent)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddQrUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
