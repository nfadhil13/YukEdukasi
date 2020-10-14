package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import com.fdev.yukedukasi.util.getRandomNumber
import com.fdev.yukedukasi.util.printLogD
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Parcelize
data class Game(
        val id: Int,
        val name: String,
        val image: String,
        val sound: String,
        val gameMateri: ArrayList<Materi> = ArrayList(),
        private val pertanyaan: ArrayList<Soal> = ArrayList()
) : Parcelable {


    fun insertMateri(materiList: List<Materi>) {
        gameMateri.addAll(materiList)
    }

    fun clearMateri() {
        gameMateri.clear()
    }

    fun getPertanyaan(): List<Soal> = pertanyaan

    fun insertPertanyaan(list: List<Soal>) {
        list.forEach { soal ->
            pertanyaan.add(soal)
        }
    }


    //Fungsi ini meng - generate pilihan ganda untuk soal
    //This fucntiion generates multiple choices for each test question
    fun generateSoalListPilihan(list: List<Soal>): List<Soal> {
        val soalList = ArrayList<Soal>()
        list.forEach { soal ->
            val editedSoal = generateSoalPilihan(soal)
            soalList.add(editedSoal)

        }
        return soalList
    }

    private fun generateSoalPilihan(soal: Soal): Soal {

        val pilihanSoalList = ArrayList<PilihanSoal>()

        val rightAnswerIndex = getMateriIndexByID(soal.correctAnswerMaterSeq)
        //Pilihan soal
        val rightAnswer = PilihanSoal(
                soal.correctAnswerMaterSeq,
                gameMateri[rightAnswerIndex].image
        )

        pilihanSoalList.add(rightAnswer)

        //generate other 2 random choice
        val otherAnswer1Index = getRandomNumber(
                intArrayOf(rightAnswerIndex),
                0,
                gameMateri.size - 1
        )
        val otherAnswer1 = PilihanSoal(
                gameMateri[otherAnswer1Index].id,
                gameMateri[otherAnswer1Index].image
        )

        pilihanSoalList.add(otherAnswer1)


        //Generate other 3 random choice
        val otherAnswer2Index = getRandomNumber(
                intArrayOf(rightAnswerIndex, otherAnswer1Index),
                0,
                gameMateri.size - 1
        )
        val otherAnswer2 = PilihanSoal(
                gameMateri[otherAnswer2Index].id,
                gameMateri[otherAnswer2Index].image
        )

        pilihanSoalList.add(otherAnswer2)

        printLogD("Random" , " $rightAnswerIndex , $otherAnswer1Index , $otherAnswer2Index ")

        Collections.swap(pilihanSoalList, 0, Random.nextInt(0 , 3))

        soal.pilihan.addAll(pilihanSoalList)
        return soal
    }

    private fun getMateriIndexByID(id : Int) : Int{
        gameMateri.forEachIndexed { index , materi ->
            if(materi.id == id) return index
        }
        return -1
    }

}