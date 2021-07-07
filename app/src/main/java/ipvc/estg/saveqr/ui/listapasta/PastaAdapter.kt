package ipvc.estg.saveqr.ui.listapasta

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.LoginActivity
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.popup_insertPasta
import ipvc.estg.saveqr.ui.listapasta.PastaAdapter.PastasViewHolder


const val ID = "ID"
const val TITULO = "TITULO"

class PastaAdapter: ListAdapter<Folders, PastaAdapter.PastasViewHolder>(PastaComparator()) {

    private var onItemClickListener: onItemClick? = null

    public fun setOnItemClick(newOnItemClickListener: PastaAdapter.onItemClick) {
        onItemClickListener = newOnItemClickListener
    }

    interface onItemClick {
        fun onViewClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastasViewHolder {
        return PastasViewHolder.create(parent, onItemClickListener)

    }

    override fun onBindViewHolder(holder: PastasViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.nome)
        holder.pastaEdit.setOnClickListener{
            val context = holder.pastaTitulo.context
            val titulo = holder.pastaTitulo.text.toString()
            val id: Int? = current.id
            val intent = Intent(context,popup_insertPasta::class.java).apply {
                putExtra(ID,id)
                putExtra(TITULO,titulo)
            }
            context.startActivity(intent)
        }
    }

    class PastasViewHolder(itemView: View, onItemClick: onItemClick?) : RecyclerView.ViewHolder(itemView) {
        val pastaTitulo: TextView = itemView.findViewById(R.id.titulo)
        val pastaData: TextView = itemView.findViewById(R.id.date2)
        val pastaAdd: ImageView = itemView.findViewById(R.id.add)
        val pastaEdit: RelativeLayout = itemView.findViewById(R.id.editar)


        fun bind(titulo: String?) {
            pastaTitulo.text = titulo

        }

        init {
            itemView.setOnClickListener { v: View ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.onViewClick(position)
                }

            }
        }
        companion object {
            fun create(parent: ViewGroup, onItemClickListener: onItemClick?): PastasViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listapasta_item, parent, false)
                return PastasViewHolder(view,onItemClickListener)
            }
        }
    }

        class PastaComparator : DiffUtil.ItemCallback<Folders>() {
            override fun areItemsTheSame(oldItem: Folders, newItem: Folders): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: Folders, newItem: Folders): Boolean {
                return oldItem.nome == newItem.nome
            }
        }
    }






