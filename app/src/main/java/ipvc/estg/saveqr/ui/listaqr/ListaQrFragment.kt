package ipvc.estg.saveqr.ui.listaqr

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.Swipes.SwipeToDeleteCallback
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.QrCodesEndpoint
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.ui.listaqr.adapter.QrAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaQrFragment : Fragment() {


    private lateinit var ListaQRviewModel: ListaQRViewModel
    var allPastasLiveData = MutableLiveData<List<Folders?>>()


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

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = QrAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(QrCodesEndpoint::class.java)

        var value = arguments?.getInt("userId")


        Toast.makeText( activity, value.toString(), Toast.LENGTH_LONG).show()
        //  val call = request.getQrCodeByUser(value)
        val call = request.getQrcodes()
        val allReportsLiveData = MutableLiveData<List<Qrcodes?>>()

        call.enqueue(object  : Callback<QrCodesReturn> {
            override fun onResponse(call: Call<QrCodesReturn>, response: Response<QrCodesReturn>) {

                if(response.isSuccessful) {
                    var arrAllReports: Array<Qrcodes?> = arrayOfNulls<Qrcodes>(response.body()!!.data.size)

                    for ((index, item) in response.body()!!.data.withIndex()) {
                        arrAllReports[index] = item
                    }

                    var allReports: List<Qrcodes?> = arrAllReports.asList()

                    allReportsLiveData.value = allReports


                    allReportsLiveData.observe(requireActivity()) { reports ->
                        reports.let { adapter.submitList(it) }
                    }

                }else{

                }
            }

            override fun onFailure(call: Call<QrCodesReturn>, t: Throwable) {
                Toast.makeText( activity, "DEU ERRO", Toast.LENGTH_LONG).show()
            }
        })

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val id: Int = allPastasLiveData.value?.get(position)?.id ?: 0
                val iduser: Int = allPastasLiveData.value?.get(position)?.userId ?: 0

                //dinamico ID ID
                //val callDelete = request.deleteFolders(id, iduser)

               /* callDelete?.enqueue(object : Callback<Folders> {
                    override fun onResponse(call: Call<Folders>, response: Response<Folders>) {

                        if (response.isSuccessful) {

                            allPastasLiveData.value =
                                allPastasLiveData.value!!.toMutableList().apply {
                                    removeAt(position)
                                }.toList()


                            Toast.makeText(requireContext(), "Sucesso", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(requireContext(), "Erro", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Folders>, t: Throwable) {
                        Toast.makeText(requireContext(), "Erro", Toast.LENGTH_LONG).show()
                    }
                })*/
            }
        }


        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return root
    }

}

