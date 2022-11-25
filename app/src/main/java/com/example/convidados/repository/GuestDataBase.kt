package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION
import android.util.Log
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context): SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object{
        private  const val NAME = "guestdb"
        private  const val VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        //Criação do Banco de Dados
        db.execSQL("CREATE TABLE "  + DataBaseConstants.GUEST.TABLE_NAME + " ( " +
                DataBaseConstants.GUEST.columns.ID  + " integer primary key autoincrement, " +
                DataBaseConstants.GUEST.columns.NAME  + " text, " +
                DataBaseConstants.GUEST.columns.PRESENCE + " integer); ")
        Log.d("Debug", "Criou a tabela")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}