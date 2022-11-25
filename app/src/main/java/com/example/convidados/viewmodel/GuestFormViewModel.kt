package com.example.convidados.viewmodel

import android.app.Application
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG

class GuestFormViewModel(application: Application) : AndroidViewModel(application){

    private val repository = GuestRepository.getInstance(application)

    private  val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private  val _saveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = _saveGuest

    fun save(guest: GuestModel){
        if (guest.id == 0){
           _saveGuest.value =  repository.insert(guest)


        } else{
           _saveGuest.value = repository.update(guest)


        }
    }
    fun get(id:Int){
        guestModel.value = repository.get(id)
    }

}