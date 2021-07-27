package ipvc.estg.saveqr.ui.listapasta

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import ipvc.estg.saveqr.QrPdf
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.Swipes.SwipeToDeleteCallback
import ipvc.estg.saveqr.Swipes.SwipeToEditCallback
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.foldersEndpoint
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.api.models.FoldersRegisterReturn
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import kotlinx.android.synthetic.main.fragment_listapasta.*
import kotlinx.android.synthetic.main.fragment_listapasta.view.*
import kotlinx.android.synthetic.main.fragment_listapasta.view.add
import kotlinx.android.synthetic.main.listapasta_item.view.*
import kotlinx.android.synthetic.main.popup_addpasta.*
import kotlinx.android.synthetic.main.popup_addpasta.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ListaPastaFragment : Fragment() {

    private lateinit var listaPastaViewModel: ListaPastaViewModel
    val adapter = PastaAdapter()
    var idUser: Int = 0
    var request = ServiceBuilder.buildService(foldersEndpoint::class.java)
    var allPastasLiveData = MutableLiveData<List<Folders?>>()


    @RequiresApi(Build.VERSION_CODES.O)
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

        val loginShared: SharedPreferences? =
            activity?.getSharedPreferences(getString(R.string.login_p), Context.MODE_PRIVATE)
        idUser = loginShared?.getInt(getString(R.string.idLogin), 0)!!
        if (idUser != null) {
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            textView4.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.nav_listaQrFragment,
                    null
                )
            )
            root.button_pdf.setOnClickListener {
                val intent = Intent(this.context, QrPdf::class.java)
                startActivity(intent)
            }

            root.add.setOnClickListener {

                val mDialogView =
                    LayoutInflater.from(requireActivity()).inflate(R.layout.popup_addpasta, null)
                val mBuilder = AlertDialog.Builder(requireActivity()).setView(mDialogView)
                val mAlertDialog = mBuilder.show()

                mDialogView.addpasta.setOnClickListener {
                    val formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")      //data
                    val usersDesiredTimeZone: ZoneId =
                        ZoneId.of("Europe/Lisbon")           //fuso Horario
                    val now: ZonedDateTime = ZonedDateTime.now(usersDesiredTimeZone)
                    val text: String = now.format(formatter)
                    val nomepasta = mDialogView.email.text.toString()

                    val pasta = mDialogView.email.text.toString()
                    if (!pasta.isNullOrEmpty()) {
                        Toast.makeText(activity, "Sucesso! - " + pasta, Toast.LENGTH_SHORT).show();
                        mAlertDialog.dismiss()
                        val request = ServiceBuilder.buildService(foldersEndpoint::class.java)
                        val call = request.postFolders(
                            nomepasta,
                            text,
                            idUser as Int?
                        )
                        call.enqueue(object : Callback<Folders> {
                            override fun onResponse(
                                call: Call<Folders>,
                                response: Response<Folders>
                            ) {


                                if (response.isSuccessful) {
                                    val call = request.getPastaByUser(idUser)
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

                                                var allReports: List<Folders?> =
                                                    arrAllReports.asList()

                                                allPastasLiveData.value = allReports


                                                allPastasLiveData.observe(requireActivity()) { reports ->
                                                    reports.let { adapter.submitList(it) }
                                                }

                                            } else {

                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<FoldersReturn>,
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

                            override fun onFailure(call: Call<Folders>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        }
                        )
                    }else{
                        Toast.makeText(activity, "Pasta sem nome", Toast.LENGTH_LONG).show()
                    }
                }
            }

            adapter.setOnItemClick(object : PastaAdapter.onItemClick {
                override fun onViewClick(position: Int) {
                    val id: Int = allPastasLiveData.value?.get(position)?.id ?: 0
                    val pastaTemp: Folders? = allPastasLiveData.value!![position]
                    val bundle = bundleOf("id" to pastaTemp!!.id,"folderId" to id)
                    findNavController().navigate(R.id.nav_listaQrFragment,bundle)

                }

            })

            val swipeHandlerEdit = object : SwipeToEditCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position: Int = viewHolder.adapterPosition
                    val id: Int = allPastasLiveData.value?.get(position)?.id ?: 0
                    val pastaTemp: Folders? = allPastasLiveData.value!![position]
                    val mDialogView =
                        LayoutInflater.from(requireActivity()).inflate(R.layout.popup_addpasta, null)
                    val mBuilder = AlertDialog.Builder(requireActivity()).setView(mDialogView)
                    val mAlertDialog = mBuilder.show()
                    val nome = mDialogView.findViewById<EditText>(R.id.email)
                    nome.setText(pastaTemp!!.nome)
                    val id1 = pastaTemp!!.id
                    val editar = mDialogView.findViewById<TextView>(R.id.textView5)
                    editar.setText("Editar Pasta")
                    mDialogView.outPop.setOnClickListener{
                        listarpasta()
                        mAlertDialog.dismiss()
                    }

                    mAlertDialog.setOnCancelListener {
                       listarpasta()
                    }
                    mDialogView.addpasta.setOnClickListener {


                        if (!TextUtils.isEmpty(nome.text)) {
                            val request = ServiceBuilder.buildService(foldersEndpoint::class.java)

                            val call = request.setUpdateFolders(id1!!,nome.text.toString())

                            call.enqueue(object  : Callback<FoldersRegisterReturn> {
                                override fun onResponse(call: Call<FoldersRegisterReturn>, response: Response<FoldersRegisterReturn>) {
                                    if (response.isSuccessful) {
                                        listarpasta()
                                        mAlertDialog.dismiss()
                                    } else {
                                        Log.d("***", "Falhou")

                                    }
                                }
                                override fun onFailure(call: Call<FoldersRegisterReturn>, t: Throwable) {
                                    Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
                                }
                            })

                        } else {
                            Toast.makeText(context, "Campos vazios", Toast.LENGTH_LONG).show()
                        }

                    }

                }

            }

            val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position: Int = viewHolder.adapterPosition
                    val id1: Int = allPastasLiveData.value?.get(position)?.id ?: 0
                    val iduser: Int = allPastasLiveData.value?.get(position)?.userId ?: 0

                        val builder = AlertDialog.Builder(activity!!)
                        builder.setMessage("Tem a certeza que quer eliminar?")
                            .setCancelable(false)
                            .setPositiveButton("Sim") { dialog, id ->
                                val callDelete = request.deleteFolders(id1, iduser)

                                callDelete?.enqueue(object : Callback<Folders> {
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
                                })

                            }
                            .setNegativeButton("Nao") { dialog, id ->
                                listarpasta()
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                }
            }


            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recyclerView)


            val itemTouchHelperEdit = ItemTouchHelper(swipeHandlerEdit)
            itemTouchHelperEdit.attachToRecyclerView(recyclerView)

        }

       /* root.add.setOnClickListener {
                var position : Int = 0
                val pastaTemp: Folders? = allPastasLiveData.value!![position]
                position = allPastasLiveData.value?.get(position)?.id?:0
                val id: Int = allPastasLiveData.value?.get(position)?.id ?: 0
                val iduser: Int = allPastasLiveData.value?.get(position)?.userId ?: 0
                val intent = Intent(requireContext(), popup_insertPasta::class.java)
                intent.putExtra("id", pastaTemp!!.id)
                startActivity(intent)
            }*/




        return root


    }

    override fun onResume() {
        listarpasta()
        super.onResume()


    }


    fun listarpasta() {
        val call = request.getPastaByUser(idUser)


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
        })
    }



}


