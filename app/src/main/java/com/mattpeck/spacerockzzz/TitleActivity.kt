package com.mattpeck.spacerockzzz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_title.*

class TitleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        startButton.setOnClickListener {
            val intent = Intent(this@TitleActivity, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
