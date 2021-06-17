package ipvc.estg.saveqr.ui.listapasta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.widget.ExpandableListView
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.saveqr.api.api.models.Folders

class ListaPastaAdapter (
    private val folders: List<Folders>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<ListaPastaAdapter.ListaPastaViewHolder>() {
    override fun getItemCount() = folders.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListaPastaViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                ipvc.estg.saveqr.R.layout.fragment_listapasta,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListaPastaViewHolder, position: Int) {

        }


    class ListaPastaViewHolder(itemView: View, onItemClick: RecyclerViewClickListener) : RecyclerView.ViewHolder(itemView) {
    }
    }
