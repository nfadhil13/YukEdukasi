package com.fdev.yukedukasi.util

import kotlin.random.Random

fun getRandomNumber(excludedNumbers : IntArray , minNumber : Int , maxNumber : Int) : Int{
    var isFound = false
    var randomNumber = 0
    while(!isFound){
        isFound = true
        randomNumber = Random.nextInt(minNumber , maxNumber.plus(1))
        for(excluded in excludedNumbers){
            if(randomNumber==excluded) isFound = false
        }
    }
    return randomNumber
}