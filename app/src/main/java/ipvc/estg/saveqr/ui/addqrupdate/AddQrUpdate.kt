package ipvc.estg.saveqr.ui.addqrupdate

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController

import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.foldersEndpoint
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.ui.addqrupdate.AddQrUpdateViewModel
import kotlinx.android.synthetic.main.add_qr_update_fragment.*
import kotlinx.android.synthetic.main.popup_addpasta.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddQrUpdate : Fragment() {
//pena

//pena3

    private lateinit var nomeQR: EditText
    private lateinit var tipoQR: EditText
    private lateinit var latlngQR: EditText
    var allPastasLiveData = MutableLiveData<List<Qrcodes?>>()

    companion object {
        fun newInstance() = AddQrUpdate()
    }

    private lateinit var viewModel: AddQrUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var value = arguments?.getInt("id")



        val name: String? = arguments?.getString("name").toString()
        val tipo: String? = arguments?.getString("tipoId").toString()
        val latlng: String? = arguments?.getString("latlng").toString()
        nomeQR.setText(name)
        tipoQR.setText(tipo)
        latlngQR.setText(latlng)

        addpasta.setOnClickListener {
            updateQR()
        }


        return inflater.inflate(R.layout.add_qr_update_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddQrUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun updateQR() {
        nomeQR = nome

        val id: Int? =  0


        if (!TextUtils.isEmpty(nomeQR.text) || !TextUtils.isEmpty(tipoQR.text) || !TextUtils.isEmpty(latlngQR.text) ) {
            val request = ServiceBuilder.buildService(foldersEndpoint::class.java)

           // val call = request.setUpdateFolders(id!!,nomeQR.text.toString())

            /*call.enqueue(object  : Callback<FoldersReturn> {
                override fun onResponse(call: Call<FoldersReturn>, response: Response<FoldersReturn>) {
                    if(response.isSuccessful) {




                    }
                }

                override fun onFailure(call: Call<FoldersReturn>, t: Throwable) {

                }
            })*/

        } else {
            Toast.makeText(context, "Campos vazios", Toast.LENGTH_LONG).show()
        }


    }

}
