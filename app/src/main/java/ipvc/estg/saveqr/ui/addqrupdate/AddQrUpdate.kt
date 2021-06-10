package ipvc.estg.saveqr.ui.addqrupdate

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.ui.addqrupdate.AddQrUpdateViewModel

class AddQrUpdate : Fragment() {
//pena
    companion object {
        fun newInstance() = AddQrUpdate()
    }

    private lateinit var viewModel: AddQrUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_qr_update_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddQrUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
