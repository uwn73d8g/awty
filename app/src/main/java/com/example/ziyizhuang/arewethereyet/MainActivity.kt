package com.example.ziyizhuang.arewethereyet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = findViewById<EditText>(R.id.message)
        val phone = findViewById<EditText>(R.id.phone)
        val num = findViewById<EditText>(R.id.num)
        val start = findViewById<Button>(R.id.start)

        start.setOnClickListener {
            if (message.text.toString() != "" && phone.text.toString() != ""
                && num.text.toString() != "" ) {
                start.text = "STOP"
                val phone1 = phone.text
                val p = "(" + phone1.substring(0,3) + ")" + phone1.substring(3,6) + "-" + phone1.substring(6)
                Toast.makeText(applicationContext, "$p: Are we there yet?", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Please fill out all the information needed!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
