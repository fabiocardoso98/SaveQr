package ipvc.estg.saveqr.ui.listaqr.adapter

import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.ui.listaqr.ListaQrFragment
import kotlinx.android.synthetic.main.lista_q_r_fragment.view.*
import kotlinx.android.synthetic.main.lista_q_r_fragment.view.add
import kotlinx.android.synthetic.main.listaqr_item.view.*


class QrAdapter : ListAdapter<Qrcodes, QrAdapter.QrViewHolder>(QrComparator()) {

    private var onItemClickListener: onItemClick? = null


    public fun setOnItemClick(newOnItemClickListener: QrAdapter.onItemClick) {
        onItemClickListener = newOnItemClickListener
    }

    interface onItemClick {
        fun onViewClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QrViewHolder {
        return QrViewHolder.create(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: QrViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.content)
        //holder.bind(current,current.name, current.content,0)
    }


    class QrViewHolder(itemView: View, onItemClick: onItemClick?) :
        RecyclerView.ViewHolder(itemView) {
        /* private lateinit var data: MutableList<Qrcodes>
         private lateinit var allReportsLiveData: MutableList<Qrcodes>
         var currentSelectedIndex = -1*/

        private val titleItemView: TextView = itemView.findViewById(R.id.categoria)
        private val descritionItemView: TextView = itemView.findViewById(R.id.count)
        //     private val labelAgrupar: TextView? = itemView.findViewById(R.id.textView41)


        fun bind(title: String?, descrition: String?) {
            // fun bind(qr: Qrcodes,title: String?, descrition: String?, index: Int) {
            val constraintLayout = itemView.findViewById<CardView>(R.id.cardviewQR)
            titleItemView.text = title
            descritionItemView.text = descrition

            /*   if(qr.selected == true){
                   itemView.setBackgroundResource(R.drawable.container_card)
               }else{

               }*/

            //   constraintLayout.setOnLongClickListener { markSelectedItem(index)}
            // constraintLayout.setOnClickListener { deselectItem(index)}

        }

        /*   fun deselectItem(index: Int){
               if(currentSelectedIndex == index){
                   currentSelectedIndex = -1
                   allReportsLiveData.get(index).selected = false
               }
           }

           fun markSelectedItem(index: Int) : Boolean{
               for(item in allReportsLiveData ){
                   item.selected = false
               }

               allReportsLiveData.get(index).selected = true
               currentSelectedIndex = index


               return true
           }*/


        init {
            itemView.rootView.add.setOnClickListener { v: View ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.onViewClick(position)
                }
            }





            itemView.setOnLongClickListener {
                //     val agrupar: String? = "Agrupar QR - Code"
                Log.d(TAG, "onNoteClick: click")
                //var cardviewqr: CardView? = null
                //      labelAgrupar?.setText(agrupar)

                itemView.setBackgroundResource(R.drawable.container_card)

                // (itemView.getBackground()).setColorFilter(Color.parseColor("#FFDE03"), PorterDuff.Mode.SRC_IN);
                //itemView.getBackground().setColorFilter(Color.parseColor("#99a8ff"), PorterDuff.Mode.SRC_ATOP);
                //    cardviewqr!!.setCardBackgroundColor(Color.parseColor("#99a8ff"))
                //return@setOnLongClickListener true
                true
            }

        }

        companion object {
            fun create(parent: ViewGroup, onItemClickListener: onItemClick?): QrViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listaqr_item, parent, false)
                return QrViewHolder(view, onItemClickListener)
            }
        }
    }

    class QrComparator : DiffUtil.ItemCallback<Qrcodes>() {
        override fun areItemsTheSame(oldItem: Qrcodes, newItem: Qrcodes): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Qrcodes, newItem: Qrcodes): Boolean {
            return oldItem.name == newItem.name
        }
    }

}


