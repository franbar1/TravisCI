package com.hexadec.megalexa.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.hexadec.megalexa.R
import com.hexadec.megalexa.adapter.GatewayController
import com.hexadec.megalexa.model.Workflow
import com.hexadec.megalexa.model.HexadecUser
import kotlinx.android.synthetic.main.activity_view.*
import android.content.Intent

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        val uLoggato = getActualUser()
        setNewWorkflowListener(uLoggato)
        setSettingListener(uLoggato)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var userAndrea = HexadecUser("1","Andrea","niente")
        GatewayController.readWorkflow(userAndrea)
    }

    private fun setSettingListener(uLoggato:HexadecUser){
        settings.setOnClickListener{
            val clickIntent = Intent(this@ViewActivity, SettingsActivity::class.java)
            clickIntent.putExtra("Utente",uLoggato);
            startActivity(clickIntent)
        }
    }

    private fun setNewWorkflowListener(uLoggato:HexadecUser) {
        fab.setOnClickListener {
            val clickIntent = Intent(this@ViewActivity, NewWorkflow::class.java)
            clickIntent.putExtra("Utente",uLoggato);
            startActivity(clickIntent)
        }
    }

    private fun getActualUser(): HexadecUser{
        val i = intent
        return i.getSerializableExtra("Utente") as HexadecUser
    }

    override fun onResume() {
        super.onResume()
        loadContent()
    }

    override fun onPause() {
        super.onPause()
        saveContent()
    }

    private fun loadContent() {

    }

    private fun saveContent() {

    }

}
