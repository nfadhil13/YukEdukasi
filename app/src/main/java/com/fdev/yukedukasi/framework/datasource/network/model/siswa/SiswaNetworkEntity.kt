package com.fdev.yukedukasi.framework.datasource.network.model.siswa


import com.google.gson.annotations.SerializedName


data class SiswaNetworkEntity(

    @SerializedName("nama_kelas")
    val namaKelas: String,

    @SerializedName("nama_siswa")
    val namaSiswa: String,

    @SerializedName("nis")
    val nis: String,

    @SerializedName("pin")
    val pin: String,

    @SerializedName("siswa_id")
    val siswaId: String
)