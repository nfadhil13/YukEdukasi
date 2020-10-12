package com.fdev.yukedukasi.framework.presentation.main.gamedetail.materi

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.databinding.MateriItemContainerBinding

class MaterListAdapter(
        private val requestManager: RequestManager,
        private val interaction: Interaction? = null
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Materi>() {

        override fun areItemsTheSame(oldItem: Materi, newItem: Materi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Materi, newItem: Materi): Boolean {
            return oldItem.image.equals(newItem.image)
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MateriItemContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return MateriListViewHolder(
                binding,
                interaction,
                requestManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MateriListViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Materi>) {
        differ.submitList(list)
    }

    class MateriListViewHolder
    constructor(
            private val binding : MateriItemContainerBinding,
            private val interaction: Interaction?,
            private val requestManager: RequestManager
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Materi) = with(binding) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            requestManager
                    .load(item.image)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_round_error_outline_24)
                    .into(binding.imageviewMateri)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Materi)
    }
}
