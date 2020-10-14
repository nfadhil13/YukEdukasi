package com.fdev.yukedukasi.business.interactors.main.gamedetail

import javax.inject.Inject

class GameDetailInteractors
@Inject
constructor(
        val getMateriOfGame: GetMateriOfGame,
        val getTestOfGame: GetTestOfGame,
        val updateTestScore: UpdateTestScore
)