package com.example.convidados.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()
    }
//função de click do botão salvar
   override fun onClick(v: View) {
       if (v.id == R.id.button_save) {
           val name = binding.editName.text.toString()
           val presence = binding.radioPresent.isChecked

//Fiz essa condição para verificar se o campo de nome está vazio, se estiver exibe uma mensagem ao usuario
          if ( name == "") {
               Toast.makeText(this, "Insira o nome do Convidado",
                   Toast.LENGTH_SHORT).show()

           } else {
              val model = GuestModel(guestId,name, presence)
              viewModel.save(model)


           }


       }
   }

    private fun observe(){
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence){
                binding.radioPresent.isChecked =true
            }else{
                binding.radioAusent.isChecked =true
            }
        })

//Exibição de mensagens de validação de: Inserção/Atualização/Falha//Exibição de mensagens de validação de: Inserção/Atualização/Falha
        viewModel.saveGuest.observe(this, Observer {
            if (it){
                if (guestId == 0){
                    Toast.makeText(applicationContext, "Inserção com sucesso", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(applicationContext, "Atualização com sucesso", Toast.LENGTH_SHORT).show()
                }
                //Esse finish mata a activity para ser atualizado a lista de nomes na All Guests
                finish()
            } else{
                Toast.makeText(applicationContext, "A operação falhou!", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)


  }



    }
}

