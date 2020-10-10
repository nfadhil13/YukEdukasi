package com.fdev.yukedukasi.framework.presentation.main.menu

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.databinding.GameItemContainerBinding
import com.fdev.yukedukasi.util.printLogD

class GameListAdapter(
        private val requestManager: RequestManager,
        private val interaction: Interaction? = null
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {

        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.equals(newItem)
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = GameItemContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                        parent,
                        false
                )

        return GameViewHolder(binding , requestManager, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Game>) {
        differ.submitList(list)
    }

    class GameViewHolder
    constructor(
            private val binding : GameItemContainerBinding,
            private val requestManager: RequestManager,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game) = with(binding) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            tvTitle.text = item.gameName
            requestManager
                    .load(item.gameIcon)
                    .into(binding.imageviewIcon)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Game)
    }


}
