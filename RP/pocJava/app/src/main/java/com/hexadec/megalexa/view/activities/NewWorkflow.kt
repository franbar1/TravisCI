package com.hexadec.megalexa.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hexadec.megalexa.R
import com.hexadec.megalexa.model.HexadecUser
import android.R.id
import android.content.Intent
import android.widget.Button
import kotlinx.android.synthetic.main.activity_view.*


class NewWorkflow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workflow)
    }

    override fun onStart() {
        super.onStart()
        val uLoggato = getActualUser()
        var ConfigurationBlock = findViewById(R.id.ConfigurationBlock) as Button

        ConfigurationBlock.setOnClickListener {
            val clickIntent = Intent(this@NewWorkflow, ConfigurationBlockText::class.java)
            clickIntent.putExtra("Utente", uLoggato);
            startActivity(clickIntent)
        }
    }

    private fun getActualUser(): HexadecUser{
        val i = intent
        return i.getSerializableExtra("Utente") as HexadecUser
    }
}

