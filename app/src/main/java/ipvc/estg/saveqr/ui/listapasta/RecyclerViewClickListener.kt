package ipvc.estg.saveqr.ui.listapasta

import android.view.View
import ipvc.estg.saveqr.api.api.models.Folders


interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, movie: Folders)
}