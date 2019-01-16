package br.com.eversee.app.appeversee


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import java.lang.Exception
import java.util.*
import android.os.Build
import android.app.Activity
import MyContextWrapper
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.security.AccessController.getContext

var lang = "es"

class ConfigurationActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        var userCode = intent.getIntExtra("userId",0)

        val btnUsageMobData = findViewById<Button>(R.id.btnUsingMobileData)
        val btnUsageWiFi = findViewById<Button>(R.id.btnUsingWiFi)
        if(userCode != 0){
            btnUsageMobData.text = "1"
        }
        val context = this

        var listNet = mutableListOf<Int>(0,0,0,0)

        var listWiFi = mutableListOf<Int>(0,0,0,0)


        var btnSelectLanguage = findViewById<Button>(R.id.btnLanguage)


        var selectedLanguage = 0

        var actualLanguage = Locale.getDefault().language

        if (actualLanguage.equals("pt")){
            selectedLanguage = 1
        }else{
            selectedLanguage = 0
        }

        btnSelectLanguage.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.pop_config_idioma, null,false)

            var builder = AlertDialog.Builder(this)

            builder.setView(view)

            var alerta = builder.create()
            alerta.setView(view)

            alerta.show()

            if (selectedLanguage == 1){
                alerta.findViewById<RadioButton>(R.id.rdoPortugues)!!.isChecked = true
            }else if(selectedLanguage == 1){
                alerta.findViewById<RadioButton>(R.id.rdoSpanish)!!.isChecked = true
            }else{
                alerta.findViewById<RadioButton>(R.id.rdoEnglish)!!.isChecked = true
            }

            var btnCancel = alerta.findViewById<Button>(R.id.btnConfigCancelI)

            btnCancel!!.setOnClickListener(object: View.OnClickListener{
                override fun onClick(arg0: View) {
                    //Toast.makeText(this@MainActivity, "alerta.dismiss()", Toast.LENGTH_SHORT).show()
                    alerta.dismiss()
                }
            })

            alerta.findViewById<Button>(R.id.btnConfigOKI)!!.setOnClickListener(object: View.OnClickListener{
                override fun onClick(arg0: View){
                    if (alerta.findViewById<RadioButton>(R.id.rdoEnglish)!!.isChecked) {
        //                  getMyContext(context,"en")
                            setLocale("en")
                            recreate()
                        selectedLanguage = 0
                    }
                    if (alerta.findViewById<RadioButton>(R.id.rdoPortugues)!!.isChecked) {
        //                setAppLocale("pt",this@ConfigurationActivity)
        //                getMyContext(context,"pt")
                        lang="pt"
                        selectedLanguage = 1
                    }
                    if (alerta.findViewById<RadioButton>(R.id.rdoSpanish)!!.isChecked) {
                        selectedLanguage = 2
                    }

                    alerta.dismiss()


                }

            })

        }

        btnUsageMobData.setOnClickListener {
            listNet = updateDialog(listNet)
        }

        btnUsageWiFi.setOnClickListener {

            listWiFi = updateDialog(listWiFi)


        }

    }

    private fun setLocale(language: String) {
        var locale = Locale(language)
        Locale.setDefault(locale)
        var config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        //https://www.youtube.com/watch?v=zILw5eV9QBQ

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, lang))
    }

    fun updateDialog (list: MutableList<Int>): MutableList<Int>{

        val view = layoutInflater.inflate(R.layout.pop_config_sinc, null,false)

        var builder = AlertDialog.Builder(this)

        builder.setView(view)

        var alerta = builder.create()
        alerta.setView(view)

        alerta.show()

        //var audio = alerta.findViewById<CheckBox>(R.id.chkboxAudio)

        var btnCancel = alerta.findViewById<Button>(R.id.btnConfigCancel)
        var btnConfirm = alerta.findViewById<Button>(R.id.btnConfigOK)

        var chkboxAudio = alerta.findViewById<CheckBox>(R.id.chkboxAudio)
        var chkboxDB = alerta.findViewById<CheckBox>(R.id.chkboxDB)
        var chkboxPicture = alerta.findViewById<CheckBox>(R.id.chkboxPicture)
        var chkboxVideo = alerta.findViewById<CheckBox>(R.id.chkboxVideo)

        try{

            chkboxAudio!!.isChecked = if (list[0]==0) false else true

            chkboxDB!!.isChecked = if (list[1]==0) false else true

            chkboxPicture!!.isChecked = if (list[2]==0) false else true

            chkboxVideo!!.isChecked = if (list[3]==0) false else true

        }
        catch(e: Exception){
            Log.i("TAG", e.message)
        }
        btnCancel!!.setOnClickListener (object : View.OnClickListener {
            override fun onClick(arg0: View) {
                //Toast.makeText(this@MainActivity, "alerta.dismiss()", Toast.LENGTH_SHORT).show()
                alerta.dismiss()
            }
        })

        btnConfirm!!.setOnClickListener ( object: View.OnClickListener {
            override fun onClick(arg0: View) {
                //Toast.makeText(this@ConfigurationActivity,"Teste",Toast.LENGTH_SHORT).show()
                try{
                    //Toast.makeText(this@ConfigurationActivity,"Teste",Toast.LENGTH_SHORT).show()
                    if (alerta.findViewById<CheckBox>(R.id.chkboxAudio)!!.isChecked) {
                        list[0] = 1
                    }else if (!findViewById<CheckBox>(R.id.chkboxAudio)!!.isChecked){
                        list[0] = 0
                    }
                }catch(ex: Exception){
                    list[0]=0
                }
                try{
                    //Toast.makeText(this@ConfigurationActivity,"Teste",Toast.LENGTH_SHORT).show()
                    if (alerta.findViewById<CheckBox>(R.id.chkboxDB)!!.isChecked) {
                        list[1] = 1
                    }else if (!findViewById<CheckBox>(R.id.chkboxDB)!!.isChecked){
                        list[1] = 0
                    }
                }catch(ex: Exception){
                    list[1]=0
                }
                try{
                    //Toast.makeText(this@ConfigurationActivity,"Teste",Toast.LENGTH_SHORT).show()
                    if (alerta.findViewById<CheckBox>(R.id.chkboxPicture)!!.isChecked) {
                        list[2] = 1
                    }else if (!findViewById<CheckBox>(R.id.chkboxPicture)!!.isChecked){
                        list[2] = 0
                    }
                }catch(ex: Exception){
                    list[2]=0
                }
                try{
                    //Toast.makeText(this@ConfigurationActivity,"Teste",Toast.LENGTH_SHORT).show()
                    if (alerta.findViewById<CheckBox>(R.id.chkboxVideo)!!.isChecked) {
                        list[3] = 1
                    }else if (!findViewById<CheckBox>(R.id.chkboxVideo)!!.isChecked){
                        list[3] = 0
                    }
                }catch(ex: Exception){
                    list[3]=0
                }

                alerta.dismiss()
            }
        })

        return list
    }



    /*fun getMyContext(context: Context, string: String): Context {
        return MyContextWrapper.wrap(context, string)
    }

    fun setAppLocale(language: String, activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val resources = activity.resources
            val configuration = resources.configuration
            configuration.setLocale(Locale(language))
            activity.applicationContext.createConfigurationContext(configuration)
        } else {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = activity.resources.configuration
            config.locale = locale
            activity.resources.updateConfiguration(config,
                    activity.resources.displayMetrics)
        }
    }*/


}
