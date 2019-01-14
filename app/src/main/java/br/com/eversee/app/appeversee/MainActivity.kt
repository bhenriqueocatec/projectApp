package br.com.eversee.app.appeversee

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.math.BigInteger
import java.security.MessageDigest
import br.com.eversee.app.appeversee.common.InternetValidation
import br.com.eversee.app.appeversee.dao.DataBaseHandler
import br.com.eversee.app.appeversee.dao.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user = findViewById<EditText>(R.id.txtLogin)

        var password =  findViewById<EditText>(R.id.txtPassword)

        var btnLogin = findViewById<Button>(R.id.btnLogin)

        val context = this

        btnLogin.setOnClickListener {


            var messageError = loginFilledupIncorrect(user, password)
            if (messageError.length != 0){
                showAlertLogin(messageError + user.text)
                password.setBackgroundResource(R.drawable.login_field_error)
                user.setBackgroundResource(R.drawable.login_field_error)
            }else{
                if (InternetValidation().verifyAvailableNetwork(this@MainActivity)){
                    showAlertLogin("With internet")
                }else{
                    var user = User(0,user.text.toString(),password.text.toString(),0,0,0
                    ,"",0,0,0)
                    var totalItens = DataBaseHandler(context).readData(user).size

                    if (totalItens==0){
                        showAlertLogin("Erro")
                    }else{
                        showAlertLogin("OK")
                    }
                }
            }






        }

        //var gerarHash="!Visual+" + user+senha.toUpperCase()
        //var hash=  function.md5hashing(gerarHash)
        //var hashGerado = hash.toUpperCase()

    }

    fun loginFilledupIncorrect(user: EditText, password: EditText): String {

        var messageError = ""

        if (user.text.length == 0) {
            messageError += getString(R.string.msg_login_error)

        }

        if (password.length() == 0 && messageError.contains(getString(R.string.msg_login_error))){
            messageError += "\n" + getString(R.string.msg_password_error)

        }else if(password.text.length == 0){
            messageError += getString(R.string.msg_password_error)
        }

        return messageError
    }

    fun md5hashing(text: String): String? {
        var hashtext: String? = null
        try {
            val m = MessageDigest.getInstance("MD5")
            m.reset()
            m.update(text.toByteArray())
            val digest = m.digest()
            val bigInt = BigInteger(1, digest)
            hashtext = bigInt.toString(16)
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext!!.length < 32) {
                hashtext = "0$hashtext"
            }
        } catch (e1: Exception) {
            // TODO: handle exception

        }

        return hashtext
    }

    private fun showAlertLogin(message: String) {

        val view = layoutInflater.inflate(R.layout.alert, null)


        var builder = AlertDialog.Builder(this)


        //builder.setTitle(resources.getString(R.string.title_error))
        builder.setMessage(message);
        builder.setView(view)
        var alerta = builder.create()
        alerta.show()

        view.findViewById<Button>(R.id.bt).setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View) {
                //Toast.makeText(this@MainActivity, "alerta.dismiss()", Toast.LENGTH_SHORT).show()
                alerta.dismiss()
            }
        })


    }


}
