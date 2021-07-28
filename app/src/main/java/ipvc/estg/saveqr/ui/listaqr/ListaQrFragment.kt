package ipvc.estg.saveqr.ui.listaqr

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.Swipes.SwipeToDeleteCallback
import ipvc.estg.saveqr.Swipes.SwipeToEditCallback
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.QrCodesEndpoint
import ipvc.estg.saveqr.api.api.endpoints.foldersEndpoint
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.api.models.QrCodeReturn
import ipvc.estg.saveqr.api.models.QrCodesReturn
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.ui.listaqr.adapter.QrAdapter
import kotlinx.android.synthetic.main.fragment_listapasta.view.*
import kotlinx.android.synthetic.main.listaqr_item.view.*
import kotlinx.android.synthetic.main.popup_addpasta.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ListaQrFragment : Fragment() {


    private lateinit var ListaQRviewModel: ListaQRViewModel
    var allReportsLiveData = MutableLiveData<List<Qrcodes?>>()
    private var isMultiSelected = MutableLiveData<Boolean>()
    val adapter = QrAdapter()
    var request = ServiceBuilder.buildService(QrCodesEndpoint::class.java)




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ListaQRviewModel = ViewModelProviders.of(this).get(ListaQRViewModel::class.java)
        val root = inflater.inflate(R.layout.lista_q_r_fragment, container, false)
        val add: ImageView = root.findViewById(R.id.add)
        val textView4: TextView = root.findViewById(R.id.textView41)
        ListaQRviewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it

        })
        add.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_addQrUpdate, null))
        textView4.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.nav_detalhesQR,
                null
            )
        )

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = QrAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())




        //Toast.makeText( activity, folderId.toString(), Toast.LENGTH_LONG).show()
        var folderId = arguments?.getInt("folderId")

        val call = request.getQrCodeByFolder(folderId)
        call.enqueue(object : Callback<QrCodesReturn> {
            override fun onResponse(call: Call<QrCodesReturn>, response: Response<QrCodesReturn>) {

                if (response.isSuccessful) {
                    var arrAllReports: Array<Qrcodes?> =
                        arrayOfNulls<Qrcodes>(response.body()!!.data.size)

                    for ((index, item) in response.body()!!.data.withIndex()) {
                        arrAllReports[index] = item
                    }

                    var allReports: List<Qrcodes?> = arrAllReports.asList()

                    allReportsLiveData.value = allReports


                    allReportsLiveData.observe(requireActivity()) { reports ->
                        reports.let { adapter.submitList(it) }
                    }

                } else {

                }
            }

            override fun onFailure(call: Call<QrCodesReturn>, t: Throwable) {
                Toast.makeText(activity, "DEU ERRO", Toast.LENGTH_LONG).show()
            }


        })


        root.rootView.move.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(requireActivity()).inflate(R.layout.popup_addpasta, null)
            val mBuilder = AlertDialog.Builder(requireActivity()).setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            mDialogView.addpasta.setOnClickListener {
                val nomepasta = mDialogView.email.text.toString()

                val pasta = mDialogView.email.text.toString()
                if (!pasta.isNullOrEmpty()) {
                    Toast.makeText(activity, "Sucesso! - " + pasta, Toast.LENGTH_SHORT).show();
                    mAlertDialog.dismiss()
                    val request = ServiceBuilder.buildService(QrCodesEndpoint::class.java)
                    /*val call = request.postFolders(
                        nomepasta,
                        text,
                        idUser as Int?
                    )*/
                    call.enqueue(object : Callback<Qrcodes> {
                        override fun onResponse(
                            call: Call<Qrcodes>,
                            response: Response<Qrcodes>
                        ) {


                            if (response.isSuccessful) {
                                val call = request.getQrCodeByFolder(folderId)
                                call.enqueue(object : Callback<QrCodesReturn> {
                                    override fun onResponse(
                                        call: Call<QrCodeReturn>,
                                        response: Response<QrCodeReturn>
                                    ) {

                                        if (response.isSuccessful) {
                                            var arrAllReports: Array<Qrcodes?> =
                                                arrayOfNulls<Qrcodes>(response.body()!!.data.size)

                                            for ((index, item) in response.body()!!.data.withIndex()) {
                                                arrAllReports[index] = item
                                            }

                                            var allReports: List<Qrcodes?> =
                                                arrAllReports.asList()

                                            allReportsLiveData.value = allReports


                                            allReportsLiveData.observe(requireActivity()) { reports ->
                                                reports.let { adapter.submitList(it) }
                                            }

                                        } else {

                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<QrCodesReturn>,
                                        t: Throwable
                                    ) {
                                        Toast.makeText(activity, "DEU ERRO", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                })
                                // communicator.passDataconn(root.nome.text.toString())
                                //  communicator.passDataconn(id,root.nome.text.toString(),root.username.text.toString(),root.email.text.toString(),root.password.text.toString())
                                // getActivity()?.getSupportFragmentManager()?.beginTransaction().remove(this@RegistarFragment).commit();


                            } else {
                                Log.d("***", "Falhou")

                            }

                        }

                        override fun onFailure(call: Call<Qrcodes>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    }
                    )
                }else{
                    Toast.makeText(activity, "Pasta sem nome", Toast.LENGTH_LONG).show()
                }
            }
        }

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
                //val folderId: Int = allReportsLiveData.value?.get(position)?.folderId?: 0

                val builder = AlertDialog.Builder(activity!!)
                builder.setMessage("Tem a certeza que quer eliminar?")
                    .setCancelable(false).setPositiveButton("Sim") { dialog, id ->

                        //dinamico ID ID
                        val callDelete = request.deleteQRcode(id)

                        callDelete?.enqueue(object : Callback<Qrcodes> {
                            override fun onResponse(
                                call: Call<Qrcodes>,
                                response: Response<Qrcodes>
                            ) {

                                if (response.isSuccessful) {

                                    allReportsLiveData.value =
                                        allReportsLiveData.value!!.toMutableList().apply {
                                            removeAt(position)
                                        }.toList()


                                    Toast.makeText(requireContext(), "Sucesso", Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    Toast.makeText(requireContext(), "Erro", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }

                            override fun onFailure(call: Call<Qrcodes>, t: Throwable) {
                                Toast.makeText(requireContext(), "Erro", Toast.LENGTH_LONG).show()
                            }
                        })
                    }.setNegativeButton("Não") { dialog, id ->
                        listarqr()
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }


        val swipeHandlerEdit = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
                val pastaTemp: Qrcodes? = allReportsLiveData.value!![position]

                val bundle = bundleOf(
                    "id" to pastaTemp!!.id,
                    "name" to pastaTemp!!.name,
                    "latlng" to pastaTemp!!.latlng,
                    "tipoId" to pastaTemp!!.categoryId
                )
                findNavController().navigate(R.id.nav_addQrUpdate, bundle)
                allReportsLiveData.value = allReportsLiveData.value!!.toMutableList().apply {
                    removeAt(position)

                    var folderId = arguments?.getInt("folderId")

                    val call = request.getQrCodeByFolder(folderId)
                    call.enqueue(object : Callback<QrCodesReturn> {
                        override fun onResponse(
                            call: Call<QrCodesReturn>,
                            response: Response<QrCodesReturn>
                        ) {

                            if (response.isSuccessful) {
                                var arrAllReports: Array<Qrcodes?> =
                                    arrayOfNulls<Qrcodes>(response.body()!!.data.size)

                                for ((index, item) in response.body()!!.data.withIndex()) {
                                    arrAllReports[index] = item
                                }

                                var allReports: List<Qrcodes?> = arrAllReports.asList()

                                allReportsLiveData.value = allReports


                                allReportsLiveData.observe(requireActivity()) { reports ->
                                    reports.let { adapter.submitList(it) }
                                }

                            } else {

                            }
                        }

                        override fun onFailure(call: Call<QrCodesReturn>, t: Throwable) {
                            Toast.makeText(activity, "DEU ERRO", Toast.LENGTH_LONG).show()
                        }
                    })


                }.toList()

            }


        }




        adapter.setOnItemClick(
            object : QrAdapter.onItemClick {
                override fun onViewClick(position: Int) {
                    val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
                    val pastaTemp: Qrcodes? = allReportsLiveData.value!![position]
                    val bundle = bundleOf("id" to pastaTemp!!.id, "QrId" to id)
                    findNavController().navigate(R.id.nav_detalhesQR, bundle)

                }
            })


        val itemTouchHelperEdit = ItemTouchHelper(swipeHandlerEdit)
        itemTouchHelperEdit.attachToRecyclerView(recyclerView)


        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return root
    }

    override fun onResume() {
        listarqr()
        super.onResume()


    }

    fun listarqr() {
        var folderId = arguments?.getInt("folderId")

        val call = request.getQrCodeByFolder(folderId)


        call.enqueue(object : Callback<QrCodesReturn> {
            override fun onResponse(
                call: Call<QrCodesReturn>,
                response: Response<QrCodesReturn>
            ) {

                if (response.isSuccessful) {
                    var arrAllReports: Array<Qrcodes?> =
                        arrayOfNulls<Qrcodes>(response.body()!!.data.size)

                    for ((index, item) in response.body()!!.data.withIndex()) {
                        arrAllReports[index] = item
                    }

                    var allReports: List<Qrcodes?> = arrAllReports.asList()

                    allReportsLiveData.value = allReports


                    allReportsLiveData.observe(requireActivity()) { reports ->
                        reports.let { adapter.submitList(it) }
                    }

                } else {

                }
            }

            override fun onFailure(call: Call<QrCodesReturn>, t: Throwable) {
                Toast.makeText(activity, "DEU ERRO", Toast.LENGTH_LONG).show()
            }
        })
    }



}





