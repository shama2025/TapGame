package com.mashaffer.mytapgame

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    // Activity Level Variables
    private lateinit var tapBtn: ImageButton; // Image button for tapping
    private lateinit var numTaps: TextView; // Records the number of taps
    private lateinit var daysLoggedIn: TextView; // Records the days logged into the app (not idle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tapBtn = findViewById(R.id.imgBtn);
        numTaps = findViewById(R.id.numTaps);

        setup();
    }

    private fun setup(){
        var taps: Int = 0;
        tapBtn.setOnClickListener({
            taps++;
            numTaps.text = "Taps: ${taps}";
        })
    }
}