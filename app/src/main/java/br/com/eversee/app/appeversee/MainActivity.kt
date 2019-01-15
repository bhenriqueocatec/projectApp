package br.com.eversee.app.appeversee

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.eversee.app.appeversee.app.AppConfig
import java.math.BigInteger
import java.security.MessageDigest
import br.com.eversee.app.appeversee.common.InternetValidation
import br.com.eversee.app.appeversee.dao.DataBaseUserHandler
import br.com.eversee.app.appeversee.dao.UserLogin
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.reflect.Method


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user = findViewById<EditText>(R.id.txtLogin)

        var password =  findViewById<EditText>(R.id.txtPassword)

        var btnLogin = findViewById<Button>(R.id.btnLogin)

        var labelError = findViewById<TextView>(R.id.txtError)

        val context = this

        btnLogin.setOnClickListener {


            var messageError = loginFilledupIncorrect(user, password, labelError)
            if (!loginFilledupIncorrect(user, password, labelError)){
                if (InternetValidation().verifyAvailableNetwork(this@MainActivity)){
                    //showAlertLogin("With internet")

                    try{



                        var gerarHash="!Visual+" + user.text.toString().trim().toUpperCase()+
                                password.text.toString().trim().toUpperCase()
                        var hash=  md5hashing(gerarHash)
                        var hashGerado = hash!!.toUpperCase()

                        val queue = Volley.newRequestQueue(this)

                        val fullString = AppConfig.URL_LOGIN + "?method=LOGIN" +"&username=" + user.text.toString() +
                                         "&password=" + password.text.toString() + "&Versao=" +
                                        "21.8" + "&IOS=" + "12.8" + "&NomeEquip=" + "iPad Air 2".replace(" ","_") +
                                        "&HASH=" + hashGerado

                        Log.i("Request URL", fullString)


                        // Instantiate the cache
                        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
                        val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
                        val requestQueue = RequestQueue(cache, network).apply {
                            start()
                        }


                        val stringRequest = object: StringRequest(Method.POST, fullString,
                                Response.Listener { response ->

                            try {

                                val jObj = JSONObject(response)
                                val error = response.contains("ERRO")

                                if (!error) {

                                    Log.i("OK", "OK")
                                    Log.println(Log.INFO,"Title","OK")

                                    var user = UserLogin(jObj.getInt("RESULTADO"),user.text.toString(),password.text.toString(),
                                            0,0,0, "",0,0,0)

                                    if (DataBaseUserHandler(context).readData(user).size == 1){
                                        DataBaseUserHandler(context).updateData(user)
                                    }else{
                                        DataBaseUserHandler(context).insertDataLogin(user)
                                    }

                                    val itConfig = Intent(this@MainActivity, ConfigurationActivity::class.java)
                                    startActivity(itConfig)

                                }else{
                                    labelError.text = "User and/or password are incorrect"
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context,"Not possible to get information. Try again later",Toast.LENGTH_SHORT).show()
                            }

                        }, Response.ErrorListener { e ->
                            showAlertLogin("Não foi possível realizar consulta")
                        })
                        {
                            override fun getParams(): Map<String, String> {
                                // Posting parameters to login url
                                val params = HashMap<String, String>()
                                params["email"] = user.text.toString()
                                params["password"] = password.text.toString()
                                return params
                            }
                        }



                        queue.add(stringRequest)


                    } catch (e: Exception) {
                        Log.e(e.message, e.message)
                        Log.i("OK", e.message)
                        //e.printStackTrace();
                    }



                }else{
                    showAlertLogin("No internet")
                    var user = UserLogin(0,user.text.toString(),password.text.toString(),0,0,0
                    ,"",0,0,0)
                    //var totalItens = DataBaseHandler(context).readData(user).size

                    /*if (totalItens==0){
                        showAlertLogin("Erro")
                    }else{
                        showAlertLogin("OK")
                    }*/
                }
            }






        }
    }

    fun loginFilledupIncorrect(user: EditText, password: EditText, errorLabel: TextView) : Boolean{

        var messageError = ""

       // showAlertLogin(messageError + user.text)

        if (user.text.isEmpty()) {
            messageError += getString(R.string.msg_login_error)
            user.setBackgroundResource(R.drawable.login_field_error)
        }

        if (password.length() == 0 && messageError.contains(getString(R.string.msg_login_error))){
            messageError += "\n" + getString(R.string.msg_password_error)
            password.setBackgroundResource(R.drawable.login_field_error)

        }else if(password.text.isEmpty()){
            messageError += getString(R.string.msg_password_error)
            password.setBackgroundResource(R.drawable.login_field_error)
        }

        if (!messageError.isEmpty()) {
            errorLabel.text = messageError
            return true
        }

        return false
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
            Log.d(e1.message,e1.message)

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

        view.findViewById<Button>(R.id.btnConfigCancel).setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View) {
                //Toast.makeText(this@MainActivity, "alerta.dismiss()", Toast.LENGTH_SHORT).show()
                alerta.dismiss()
            }
        })


    }


}
