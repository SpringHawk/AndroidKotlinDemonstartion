package com.circularfashion

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View

import kotlinx.android.synthetic.main.activity_settings.*

class ActivitySettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun sendMessage(view: View) {
        val intent = Intent(this@ActivitySettings, ActivitySender::class.java)
        startActivity(intent)
    }

}
