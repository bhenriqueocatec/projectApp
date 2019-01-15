package br.com.eversee.app.appeversee

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import kotlin.math.log

class ConfigurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)


        val btnUsageMobData = findViewById<Button>(R.id.btnUsingMobileData)


        val context = this

        var list = mutableListOf<Int>(0,0,0,0)

        btnUsageMobData.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.pop_config_sinc, null,false)


            var builder = AlertDialog.Builder(this)

            builder.setView(view)



            var alerta = builder.create()
            alerta.show()

            var audio = findViewById<CheckBox>(R.id.chkboxAudio)

            view.findViewById<Button>(R.id.btnConfigCancel).setOnClickListener(object : View.OnClickListener {
                override fun onClick(arg0: View) {
                    //Toast.makeText(this@MainActivity, "alerta.dismiss()", Toast.LENGTH_SHORT).show()
                    alerta.dismiss()
                }
            })

            view.findViewById<Button>(R.id.btnConfigOK).setOnClickListener ( object: View.OnClickListener {
                override fun onClick(arg0: View) {
                    if (audio.isChecked) {
                        list[0] = 1
                    }else if (!findViewById<CheckBox>(R.id.chkboxAudio).isChecked){
                        list[0] = 0
                    }
                }

            })

            Log.i("A",list.toString())
            Log.i("A", "A")
        }



    }
}
