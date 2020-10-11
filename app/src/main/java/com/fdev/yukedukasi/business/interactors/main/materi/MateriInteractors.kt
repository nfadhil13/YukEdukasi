package com.fdev.yukedukasi.business.interactors.main.materi

import com.fdev.yukedukasi.business.interactors.main.menu.GetAllGame
import javax.inject.Inject

class MateriInteractors
@Inject
constructor(
        val getMateriOfGame: GetMateriOfGame
)