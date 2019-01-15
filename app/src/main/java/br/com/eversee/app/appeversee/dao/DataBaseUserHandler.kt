package br.com.eversee.app.appeversee.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

val DATABASE_NAME = "Siga"
val TABLE_NAME_CLI = "Tab_Painel_Usuarios"
val COL_ID ="id"
val COL_USUARIO = "usuario"
val COL_SENHA = "senha"
val COL_ATIVADO = "ativado"
val COL_ADMIN = "admin"
val COL_SYSTEM = "system"
val COL_NOME_COMPLETO = "nome_completo"
val COL_ID_EMPRESA = "id_empresa"
val COL_ID_CLIENTE = "id_cliente"
val COL_TIPO_AVALIACAO = "tipo_avaliacao"

class DataBaseUserHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,1 ){
    override fun onCreate(dbCli: SQLiteDatabase?) {
        val createTable =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CLI + " (" +
                        COL_ID + " INTEGER PRIMARY KEY, " +
                        COL_USUARIO + " VARCHAR(50), " +
                        COL_SENHA + " VARCHAR(20), " +
                        COL_ATIVADO + " INTEGER, " +
                        COL_ADMIN + " INTEGER, " +
                        COL_SYSTEM + " INTEGER, " +
                        COL_NOME_COMPLETO + " VARCHAR(50), " +
                        COL_ID_EMPRESA + " INTEGER, " +
                        COL_ID_CLIENTE + " INTEGER, " +
                        COL_TIPO_AVALIACAO + " INTEGER)"

        Log.i("BD", createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
       //To change body of created functions use File | Settings | File Templates.
    }

    fun insertDataLogin(user : UserLogin){


        val dbCli = this.writableDatabase

        var cv = ContentValues()
        cv.put(COL_ID, user.id)
        cv.put(COL_USUARIO, user.usuario)
        cv.put(COL_SENHA, user.senha)
        cv.put(COL_ATIVADO, user.ativado)
        cv.put(COL_ADMIN, user.admin)
        cv.put(COL_SYSTEM, user.system)
        cv.put(COL_NOME_COMPLETO, user.nome_completo)
        cv.put(COL_ID_EMPRESA, user.id_empresa)
        cv.put(COL_ID_CLIENTE, user.id_cliente)
        cv.put(COL_TIPO_AVALIACAO, user.tipo_avaliacao)
        var result = dbCli.insert(TABLE_NAME_CLI,null,cv)

        if(result == (-1).toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()

        dbCli.close()
    }

    fun updateData(user: UserLogin){

        try{
            val db = this.writableDatabase

            var cv = ContentValues()
            cv.put(COL_USUARIO, user.usuario)
            cv.put(COL_SENHA, user.senha)
            cv.put(COL_ATIVADO, user.ativado)
            cv.put(COL_ADMIN, user.admin)
            cv.put(COL_SYSTEM, user.system)
            cv.put(COL_NOME_COMPLETO, user.nome_completo)
            cv.put(COL_ID_EMPRESA, user.id_empresa)
            cv.put(COL_ID_CLIENTE, user.id_cliente)
            cv.put(COL_TIPO_AVALIACAO, user.tipo_avaliacao)

            db.update(TABLE_NAME_CLI,cv," id=" + user.id,null)
        }catch(updateDBError: Exception){
            Log.i("Error", updateDBError.message)
        }



    }

    fun readData(puser: UserLogin) : MutableList<UserLogin>{
        var list : MutableList<UserLogin> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME_CLI + " where ID = " + puser.id


        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var user = UserLogin(0,"","",0,0,0,
                        "",0,0,0)

                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.usuario = result.getString(result.getColumnIndex(COL_USUARIO))
                user.senha = result.getString(result.getColumnIndex(COL_SENHA))
                list.add(user)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }



}