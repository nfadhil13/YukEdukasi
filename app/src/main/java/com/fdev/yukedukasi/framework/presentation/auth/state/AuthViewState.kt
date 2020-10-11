package com.fdev.yukedukasi.framework.presentation.auth.state

import android.os.Parcelable
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthViewState(

        var splashViewState : SplashViewState? = null,

        var loginViewState: LoginViewState? = null

) : Parcelable

@Parcelize
data class SplashViewState(
        var siswa : Siswa?  = null
) : Parcelable


@Parcelize
data class LoginViewState(
        var siswa : Siswa?  = null,
        var user : User? = null,
        var nis : String = "",
        var pin : String = ""
) : Parcelable