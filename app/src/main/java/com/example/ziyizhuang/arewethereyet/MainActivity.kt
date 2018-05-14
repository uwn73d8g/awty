package com.example.ziyizhuang.arewethereyet

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.util.Log
import java.util.*


class MainActivity : AppCompatActivity() {
    var PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = findViewById<EditText>(R.id.message)
        val phone = findViewById<EditText>(R.id.phone)
        val num = findViewById<EditText>(R.id.num)
        val start = findViewById<Button>(R.id.start)
        var t = false

        start.setOnClickListener {
            val timer = Timer()
            val handler = Handler()
            if (start.text == "STOP") {
                Log.i("MainActivity" ,"entered the if")
                start.text = "start"
                t = true
            } else if (start.text == "start" &&
                    message.text.toString() != "" && phone.text.toString() != ""
                    && num.text.toString() != "" ) {
                start.text = "STOP"
                val phone1 = phone.text.toString()
                val p = "(" + phone1.substring(0,3) + ")" + phone1.substring(3,6) + "-" + phone1.substring(6)
                val gap = num.text.toString().toLong()
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(android.Manifest.permission.SEND_SMS),
                            PERMISSION_REQUEST_CODE)
                } else { // permission granted
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            runOnUiThread {
                                val sms = SmsManager.getDefault()
                                val messages = sms.divideMessage(message.text.toString())
                                for (msg in messages) {
                                    val sentIntent = PendingIntent.getBroadcast(applicationContext, 0, Intent("SMS_SENT"), 0)
                                    val deliveredIntent = PendingIntent.getBroadcast(applicationContext, 0, Intent("SMS_DELIVERED"), 0)
                                    if (t) {
                                        t = false
                                        timer.cancel()
                                        break
                                    }
                                    sms.sendTextMessage(p, null, msg, sentIntent, deliveredIntent)
                                }
                            }
                        }
                    }, 0, gap * 60 * 1000)
                }
            } else if (message.text.toString() == "" || phone.text.toString() == ""
                    && num.text.toString() == "" ) {
                Toast.makeText(applicationContext, "Please fill out all the information needed!", Toast.LENGTH_LONG).show()
            }
        }
    }

}
