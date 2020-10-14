package com.fdev.yukedukasi.framework.presentation.main.gamedetail.test

import android.text.BoringLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Answer
import com.fdev.yukedukasi.business.domain.model.PilihanSoal
import com.fdev.yukedukasi.business.domain.model.Soal
import com.fdev.yukedukasi.databinding.SoalItemContainerBinding
import com.fdev.yukedukasi.framework.presentation.changeBackground
import com.fdev.yukedukasi.framework.presentation.changeTextcolor
import com.fdev.yukedukasi.util.printLogD

class SoalListAdapter(
        private val requestManager: RequestManager,
        private val interaction: Interaction? = null) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Soal>() {

        override fun areItemsTheSame(oldItem: Soal, newItem: Soal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Soal, newItem: Soal): Boolean {
            return oldItem.pilihan.equals(newItem.pilihan)
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SoalItemContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return SoalViewHolder(binding, requestManager, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SoalViewHolder -> {
                holder.bind(differ.currentList.get(position) , position+1 == differ.currentList.size)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Soal>) {
        differ.submitList(list)
    }

    class SoalViewHolder
    constructor(
            private val binding: SoalItemContainerBinding,
            private val requestManager: RequestManager,
            private val interaction: Interaction?,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var choosenAnswer = -1

        private lateinit var soal : Soal

        private var isSumbitAnswer = false

        private var isLastSoal = false

        fun bind(item: Soal , isLast : Boolean) = with(binding) {

            soal = item

            isLastSoal = isLast

            initImage(item)

            printLogD("Test" , " ${isLast}")


            cardviewIcon.setOnClickListener {
                interaction?.playSound(soal)
            }

            cardViewAnswerChoice1.setOnClickListener {
                choosenAnswer = 0
                enableCardView1()
                if(!btnSubmit.isEnabled) enableButton()
            }

            cardViewAnswerChoice2.setOnClickListener {
                choosenAnswer = 1
                enableCardView2()
                if(!btnSubmit.isEnabled)enableButton()
            }

            cardViewAnswerChoice3.setOnClickListener {
                choosenAnswer = 2
                enableCardView3()
                if(!btnSubmit.isEnabled) enableButton()
            }
            btnSubmit.setOnClickListener {
                if(!isSumbitAnswer){
                    isSumbitAnswer = true
                    submitAnswer()
                }else{
                    interaction?.goToNextPage(adapterPosition)
                }
            }

            disableButton()

        }

        private fun enableCardView1() {
            binding.apply {
                cardViewAnswerChoice1.strokeWidth = 8
                cardViewAnswerChoice2.strokeWidth = 0
                cardViewAnswerChoice3.strokeWidth = 0
            }
        }

        private fun enableCardView2() {
            binding.apply {
                cardViewAnswerChoice1.strokeWidth = 0
                cardViewAnswerChoice2.strokeWidth = 8
                cardViewAnswerChoice3.strokeWidth = 0
            }
        }

        private fun enableCardView3() {
            binding.apply {
                cardViewAnswerChoice1.strokeWidth = 0
                cardViewAnswerChoice2.strokeWidth = 0
                cardViewAnswerChoice3.strokeWidth = 8
            }
        }

        private fun disableButton() {
            binding.apply {
                btnSubmit.changeBackground(R.drawable.disable_button)
                btnSubmit.changeTextcolor(R.color.blur)
                btnSubmit.isEnabled = false

            }
        }

        private fun enableButton() {
            binding.apply {
                btnSubmit.changeBackground(R.drawable.green_button)
                btnSubmit.changeTextcolor(R.color.colorAccent)
                btnSubmit.isEnabled = true
            }
        }


        private fun submitAnswer() {
            val isTrue = soal.isAnswerRight(soal.pilihan[choosenAnswer].materiId)
            if(!isTrue) binding.btnSubmit.changeBackground(R.drawable.red_button)
            if(!isLastSoal){
                binding.btnSubmit.text = binding.root.context.getString(R.string.next_label)
            }else{
                binding.btnSubmit.text = binding.root.context.getString(R.string.assign)
            }
            interaction?.onAnswerSumbited(isTrue , adapterPosition ,
                        Answer(soal.gameTestId , soal.pilihan[choosenAnswer].materiId)
                    )
        }

        private fun initImage(soal: Soal) {
            binding.apply {
                requestManager
                        .load(soal.pilihan[0].image)
                        .into(imageviewAnswerChoice1)

                requestManager
                        .load(soal.pilihan[1].image)
                        .into(imageviewAnswerChoice2)

                requestManager
                        .load(soal.pilihan[2].image)
                        .into(imageviewAnswerChoice3)
            }
        }
    }

    interface Interaction {
        fun onAnswerSumbited(isTrue: Boolean , position: Int , answer: Answer)
        fun playSound(soal : Soal)
        fun goToNextPage(currrentPosition : Int)
    }
}
