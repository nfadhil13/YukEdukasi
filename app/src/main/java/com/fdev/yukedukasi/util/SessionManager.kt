package com.fdev.yukedukasi.util

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
    This is session manager class
    This class manage everthing about user session
    This class is singleton
 */

class SessionManager
@Inject
constructor(
        val sharedPreferences: SharedPreferences,
        val prefEditor: SharedPreferences.Editor
){

    companion object{
        // Key for User's  NIS sharedprefrences
        const val USER_NIS_KEY = "com.fdev.yukedukasi.util.SessionManager.nis"

        //Key for user's PIN sharedPreferences
        const val USER_PIN_KEY = "com.fdev.yukedukasi.util.SessionManager.pin"
    }



    private val _currentUser = MutableLiveData<Siswa?>()

    val currentUser : LiveData<Siswa?>
        get() = _currentUser

    fun login(newSiswa : Siswa){
        prefEditor.putString(USER_NIS_KEY , newSiswa.nis)
        prefEditor.putString(USER_PIN_KEY , newSiswa.pin)
        setValue(newSiswa)
    }

    // lOG Out
    fun logOut(){
        prefEditor.remove(USER_NIS_KEY)
        prefEditor.remove(USER_PIN_KEY)
        setValue(null)
    }

    //Check last user login
    fun checkLastUserLogIn() : User?{
        val userNIS = sharedPreferences.getString(USER_NIS_KEY , null)
        userNIS?.let{
            val userPIN = sharedPreferences.getString(USER_PIN_KEY , null)
            userPIN?.let{
                return User(userNIS , userPIN)
            }
        }
        return null
    }

    fun setCurrentSiswa(currentSiswa: Siswa){
        setValue(currentSiswa)
    }


    private fun setValue(newSiswa : Siswa?){
        CoroutineScope(Main).launch{
            if(_currentUser.value != newSiswa){
                _currentUser.value = newSiswa
            }
        }
    }
}