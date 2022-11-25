package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract.Data
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
// Esse é o famoso CRUD
class GuestRepository private constructor(context: Context){

    private val guestDataBase = GuestDataBase(context)

    // Singleton
    companion object{
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }
    //Codigo do Create
    fun insert(guest: GuestModel): Boolean {
      return try {
           val db = guestDataBase.writableDatabase
           val presence = if (guest.presence) 1 else 0

          val values = ContentValues()
          values.put( DataBaseConstants.GUEST.columns.PRESENCE, presence)
          values.put( DataBaseConstants.GUEST.columns.NAME, guest.name)


             db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
             true
       } catch (e: Exception){
            false
       }
    }

    //Codigo do Update
    fun update(guest: GuestModel): Boolean{
       return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.columns.PRESENCE, presence)
            values.put(DataBaseConstants.GUEST.columns.NAME, guest.name)

            val selection = DataBaseConstants.GUEST.columns.ID + "= ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection,args)
           true
        } catch (e: Exception){

            false

        }
    }
    //Codigo do Delete
    fun delete (id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.columns.ID + " = ? "
            val args = arrayOf(id.toString())


            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception){
            false
        }
    }
    //Listagem do db
    fun getAll(): List<GuestModel>{
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.columns.ID,
                DataBaseConstants.GUEST.columns.NAME,
                DataBaseConstants.GUEST.columns.PRESENCE
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection,
                null, null, null,null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()){
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.PRESENCE))

                   list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception){
            return list
        }

        return list
    }
    //Listagem de Presentes
    fun getPresent(): List<GuestModel>{
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase


            val cursor = db.rawQuery( "SELECT id, name, presence FROM Guest WHERE presence = 1",
                null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()){
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception){
            return list
        }

        return list
    }
    //listagem de Ausentes
    fun getAbsent(): List<GuestModel>{
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase

            val cursor = db.rawQuery( "SELECT id, name, presence FROM Guest WHERE presence = 0",
                null)


            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()){
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception){
            return list
        }

        return list
    }
    //Alteração de convidados
    fun get(id: Int):GuestModel?{
       var guest: GuestModel? = null
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.columns.ID,
                DataBaseConstants.GUEST.columns.NAME,
                DataBaseConstants.GUEST.columns.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.columns.ID + " = ? "
            val args = arrayOf(id.toString())

            val cursor = db.query(
               DataBaseConstants.GUEST.TABLE_NAME, projection,
                selection, args, null,null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()){

                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.columns.PRESENCE))

                    guest = GuestModel(id, name, presence == 1)
                }
            }
            cursor.close()
        } catch (e: Exception){
            return guest
        }

        return guest
    }
}

