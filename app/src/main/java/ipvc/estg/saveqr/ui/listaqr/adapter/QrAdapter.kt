package ipvc.estg.saveqr.ui.listaqr.adapter

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.models.Qrcodes


class QrAdapter : ListAdapter<Qrcodes, QrAdapter.QrViewHolder>(QrComparator()) {

    private var onItemClickListener : onItemClick? = null


    public fun setOnItemClick(newOnItemClickListener: QrAdapter.onItemClick) {
        onItemClickListener= newOnItemClickListener
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
    }

    class QrViewHolder(itemView: View, onItemClick: onItemClick?) : RecyclerView.ViewHolder(itemView) {
        private val titleItemView: TextView = itemView.findViewById(R.id.categoria)
        private val descritionItemView: TextView = itemView.findViewById(R.id.count)

        fun bind(title: String?, descrition: String?) {
            titleItemView.text = title
            descritionItemView.text = descrition
        }

        init {
            itemView.setOnClickListener { v: View ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.onViewClick(position)
                }
            }

            itemView.setOnLongClickListener{
                Log.d(TAG,"onNoteClick: click")
                //var cardviewqr: CardView

                (itemView.getBackground()).setColorFilter(Color.parseColor("#FFDE03"), PorterDuff.Mode.SRC_IN);
                //itemView.getBackground().setColorFilter(Color.parseColor("#99a8ff"), PorterDuff.Mode.SRC_ATOP);
                //cardviewqr.setCardBackgroundColor(Color.parseColor("#99a8ff"))
                return@setOnLongClickListener true
            }
        }

        companion object {
            fun create(parent: ViewGroup, onItemClickListener: onItemClick?): QrViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listaqr_item, parent, false)
                return QrViewHolder(view,onItemClickListener)
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
