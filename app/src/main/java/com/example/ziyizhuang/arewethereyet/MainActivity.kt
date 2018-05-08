package com.example.ziyizhuang.arewethereyet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Handler
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = findViewById<EditText>(R.id.message)
        val phone = findViewById<EditText>(R.id.phone)
        val num = findViewById<EditText>(R.id.num)
        val start = findViewById<Button>(R.id.start)

        start.setOnClickListener {
            val handler = Handler()
            if (start.text == "STOP") {
                start.text = "start"
                handler.removeCallbacks(null)
            } else if (start.text == "start" &&
                    message.text.toString() != "" && phone.text.toString() != ""
                    && num.text.toString() != "" ) {
                start.text = "STOP"
                val phone1 = phone.text.toString()
                val p = "(" + phone1.substring(0,3) + ")" + phone1.substring(3,6) + "-" + phone1.substring(6)
                val timer = Timer()
                val gap = num.text.toString().toLong()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            val toast = Toast.makeText(
                                    applicationContext, "$p: Are we there yet?",
                                    Toast.LENGTH_SHORT)
                            toast.show()
                            handler.postDelayed({ toast.cancel() }, gap * 60 * 1000)
                        }
                    }
                }, 0, gap * 60 * 1000)
            } else if (message.text.toString() == "" || phone.text.toString() == ""
                    && num.text.toString() == "" ) {
                Toast.makeText(applicationContext, "Please fill out all the information needed!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
