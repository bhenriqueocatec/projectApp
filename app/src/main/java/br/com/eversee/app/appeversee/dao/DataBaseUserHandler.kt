package br.com.eversee.app.appeversee.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

val DATABASE_NAME = "Siga"
val TABLE_NAME_CLI = "Tab_Painel_Usuarios"
val COL_ID ="ID"
val COL_USUARIO = "USUARIO"
val COL_SENHA = "SENHA"
val COL_ATIVADO = "ATIVADO"
val COL_ADMIN = "ADMIN"
val COL_SYSTEM = "SYSTEM"
val COL_NOME_COMPLETO = "NOME_COMPLETO"
val COL_ID_EMPRESA = "ID_EMPRESA"
val COL_ID_CLIENTE = "ID_CLIENTE"
val COL_TIPO_AVALIACAO = "TIPO_AVALIACAO"
val COL_LOGADO = "LOGADO"
val COL_PODE_BAIXAR_NC = "PODE_BAIXAR_NC"
val COL_ACESSAR_TODAS_OBRAS = "ACESSAR_TODAS_OBRAR"
val COL_AVALIACAO_PLANO_ACAO = "AVALIACAO_PLANO_ACAO"

class DataBaseUserHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,1 ){
    override fun onCreate(dbCli: SQLiteDatabase?) {
        val createTable =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CLI + " (" +
                        COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                        COL_USUARIO + " VARCHAR(50,0), " +
                        COL_SENHA + " VARCHAR(20,0), " +
                        COL_ATIVADO + " INTEGER(4, 0), " +
                        COL_ADMIN + " INTEGER(4,0), " +
                        COL_SYSTEM + " INTEGER(4,0), " +
                        COL_NOME_COMPLETO + " VARCHAR(50,0), " +
                        COL_ID_EMPRESA + " INTEGER(4,0), " +
                        COL_ID_CLIENTE + " INTEGER(4,0), " +
                        COL_TIPO_AVALIACAO + " INTEGER(4,0), " +
                        COL_LOGADO + " INTEGER," +
                        COL_PODE_BAIXAR_NC + " INTEGER, "+
                        COL_ACESSAR_TODAS_OBRAS + " INTEGER, " +
                        COL_AVALIACAO_PLANO_ACAO + " INTEGER)"

        Log.i("BD", createTable)

        dbCli!!.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
       //To change body of created functions use File | Settings | File Templates.
    }



    fun insertDataLogin(user : UserLogin): MutableList<UserLogin>{


        val dbCli = this.writableDatabase

        var cv = ContentValues()
        //cv.put(COL_ID, user.id)
        cv.put(COL_USUARIO, user.usuario)
        cv.put(COL_SENHA, user.senha)
        cv.put(COL_ATIVADO, user.ativado)
        cv.put(COL_ADMIN, user.admin)
        cv.put(COL_SYSTEM, user.system)
        cv.put(COL_NOME_COMPLETO, user.nome_completo)
        cv.put(COL_ID_EMPRESA, user.id_empresa)
        cv.put(COL_ID_CLIENTE, user.id_cliente)
        cv.put(COL_TIPO_AVALIACAO, user.tipo_avaliacao)
        cv.put(COL_LOGADO, user.logado)
        cv.put(COL_PODE_BAIXAR_NC, user.pode_baixar_nc)
        cv.put(COL_ACESSAR_TODAS_OBRAS, user.acessar_todos_projetos)
        cv.put(COL_AVALIACAO_PLANO_ACAO, user.avaliacao_plano_acao)
        var result = dbCli.insert(TABLE_NAME_CLI,null,cv)

        /*if(result == (-1).toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()*/

        dbCli.close()

        return readData(user)
    }

    fun updateData(user: UserLogin): MutableList<UserLogin>{

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

        return readData(user)

    }

    fun readData(puser: UserLogin) : MutableList<UserLogin>{
        var list : MutableList<UserLogin> = ArrayList()


        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME_CLI + " where " + COL_USUARIO + " = '" + puser.usuario +
                "' AND " + COL_SENHA + " = '" + puser.senha + "'"


        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var user = UserLogin(0,"","",0,0,0,
                        "",0,0,0,
                        0,0,0,0)

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