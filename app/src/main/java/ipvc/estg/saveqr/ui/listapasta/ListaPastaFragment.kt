package ipvc.estg.saveqr.ui.listapasta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.ui.listaqr.adapter.PastaAdapter
import kotlinx.android.synthetic.main.fragment_listapasta.*
import kotlinx.android.synthetic.main.fragment_listapasta.view.*
import kotlinx.android.synthetic.main.popup_addpasta.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = PastaAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(ipvc.estg.saveqr.api.api.endpoints.foldersEndpoint::class.java)
        val call = request.getFolders()
        val allReportsLiveData = MutableLiveData<List<Folders?>>()

        call.enqueue(object  : Callback<FoldersReturn> {
            override fun onResponse(call: Call<FoldersReturn>, response: Response<FoldersReturn>) {

                if(response.isSuccessful) {
                    var arrAllReports: Array<Folders?> = arrayOfNulls<Folders>(response.body()!!.data.size)

                    for ((index, item) in response.body()!!.data.withIndex()) {
                        arrAllReports[index] = item
                    }

                    var allReports: List<Folders?> = arrAllReports.asList()

                    allReportsLiveData.value = allReports


                    allReportsLiveData.observe(requireActivity()) { reports ->
                        reports.let { adapter.submitList(it) }
                    }

                }else{

                }
            }

            override fun onFailure(call: Call<FoldersReturn>, t: Throwable) {
                Toast.makeText( activity, "DEU ERRO", Toast.LENGTH_LONG).show()
            }
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
