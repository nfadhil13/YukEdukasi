package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
        val id : Int,
        val name : String,
        val image : String,
        val sound : String,
        val gameMateri : ArrayList<Materi> = ArrayList()
) : Parcelable {

    fun isMateriNull() = gameMateri.isEmpty()

    fun insertMateri(materiList : List<Materi>){
        gameMateri.addAll(materiList)
    }

    fun clearMateri(){
        gameMateri.clear()
    }
}