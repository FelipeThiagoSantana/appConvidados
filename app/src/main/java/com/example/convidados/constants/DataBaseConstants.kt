package com.example.convidados.constants

import android.provider.ContactsContract.Presence

class DataBaseConstants private constructor(){

    object GUEST{
//Nome da tabela e colunas
        const val ID = "guestid"
        const val TABLE_NAME = "Guest"
//coluna
        object columns{
            const val ID = "id"
            const val  NAME = "name"
            const val PRESENCE = "presence"
        }
    }


}