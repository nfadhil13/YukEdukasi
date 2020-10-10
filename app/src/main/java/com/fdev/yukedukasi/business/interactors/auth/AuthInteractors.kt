package com.fdev.yukedukasi.business.interactors.auth

import javax.inject.Inject

class AuthInteractors
@Inject
constructor(
        val loginInCase : LogIn,
        val sycnSiswaCase: SycnSiswa
)