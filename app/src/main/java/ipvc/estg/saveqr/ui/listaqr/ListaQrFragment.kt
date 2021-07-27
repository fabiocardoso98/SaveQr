package ipvc.estg.saveqr.ui.listaqr

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.Navigation.createNavigateOnClickListener
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.Swipes.SwipeToDeleteCallback
import ipvc.estg.saveqr.Swipes.SwipeToEditCallback
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.QrCodesEndpoint
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.ui.listapasta.PastaAdapter
import ipvc.estg.saveqr.ui.listaqr.adapter.QrAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaQrFragment : Fragment() {


    private lateinit var ListaQRviewModel: ListaQRViewModel

    var allReportsLiveData = MutableLiveData<List<Qrcodes?>>()
    var contentQr=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        ListaQRviewModel= ViewModelProviders.of(this).get(ListaQRViewModel::class.java)
        val root = inflater.inflate(R.layout.lista_q_r_fragment, container, false)
        val add: ImageView = root.findViewById(R.id.add)
        val textView4: TextView = root.findViewById(R.id.textView4)
        ListaQRviewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it

        })
        add.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_addQrUpdate, null))
    //    textView4.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_detalhesQR, null))

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = QrAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(QrCodesEndpoint::class.java)

        var folderId = arguments?.getInt("folderId")



        val call = request.getQrCodeByFolder(folderId)
        call.enqueue(object  : Callback<QrCodesReturn> {
            override fun onResponse(call: Call<QrCodesReturn>, response: Response<QrCodesReturn>) {

                if(response.isSuccessful) {
                    var arrAllReports: Array<Qrcodes?> = arrayOfNulls<Qrcodes>(response.body()!!.data.size)
                    for ((index, item) in response.body()!!.data.withIndex()) {
                        arrAllReports[index] = item
                        contentQr = contentQr+";"+item.content
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
                val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
                //val folderId: Int = allReportsLiveData.value?.get(position)?.id?: 0

                //dinamico ID ID
                val callDelete = request.deleteQRcode(id)

                callDelete?.enqueue(object : Callback<Qrcodes> {
                    override fun onResponse(call: Call<Qrcodes>, response: Response<Qrcodes>) {

                        if (response.isSuccessful) {

                            allReportsLiveData.value =
                                allReportsLiveData.value!!.toMutableList().apply {
                                    removeAt(position)
                                }.toList()


                            Toast.makeText(requireContext(), "Sucesso", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(requireContext(), "Erro", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Qrcodes>, t: Throwable) {
                        Toast.makeText(requireContext(), "Erro", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }


        val swipeHandlerEdit = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
                val pastaTemp: Qrcodes? = allReportsLiveData.value!![position]
                val bundle = bundleOf("id" to pastaTemp!!.id,"name" to pastaTemp!!.name, "latlng" to pastaTemp!!.latlng, "tipoId" to pastaTemp!!.categoryId )
                findNavController().navigate(R.id.nav_addQrUpdate,bundle)
                allReportsLiveData.value = allReportsLiveData.value!!.toMutableList().apply {
                    removeAt(position)

                    /* val call = request.getPastaByUser(idUser)
                     call.enqueue(object : Callback<FoldersReturn> {
                         override fun onResponse(
                             call: Call<FoldersReturn>,
                             response: Response<FoldersReturn>
                         ) {

                             if (response.isSuccessful) {
                                 var arrAllReports: Array<Folders?> =
                                     arrayOfNulls<Folders>(response.body()!!.data.size)

                                 for ((index, item) in response.body()!!.data.withIndex()) {
                                     arrAllReports[index] = item
                                 }

                                 var allReports: List<Folders?> = arrAllReports.asList()

                                 allPastasLiveData.value = allReports


                                 allPastasLiveData.observe(requireActivity()) { reports ->
                                     reports.let { adapter.submitList(it) }
                                 }

                             } else {

                             }
                         }

                         override fun onFailure(call: Call<FoldersReturn>, t: Throwable) {
                             Toast.makeText(activity, "DEU ERRO", Toast.LENGTH_LONG).show()
                         }
                     })*/


                }.toList()

            }


        }

        adapter.setOnItemClick(object : QrAdapter.onItemClick {
            override fun onViewClick(position: Int) {
                val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
                val pastaTemp: Qrcodes? = allReportsLiveData.value!![position]
                val bundle = bundleOf("id" to pastaTemp!!.id,"QrId" to id)
                findNavController().navigate(R.id.nav_detalhesQR,bundle)

            }

        })

        val itemTouchHelperEdit = ItemTouchHelper(swipeHandlerEdit)
        itemTouchHelperEdit.attachToRecyclerView(recyclerView)


        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return root
    }
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.multi_qr)
        item.isVisible = isVisible
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.multi_qr -> {
                val bundle = bundleOf("content" to contentQr)
                findNavController().navigate(R.id.nav_detalhesQR,bundle)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

