package com.ugurrsnr.pomodorotimer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.ugurrsnr.pomodorotimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var workingTimer : CountDownTimer? = null
    private var breakTimer : CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)

        binding.startTimerButton.setOnClickListener {

            startWorkingTimer()
        }
        binding.stopTimerButton.setOnClickListener {
            workingTimer?.let {
                it.cancel()
            }
            breakTimer?.let {
                it.cancel()
            }
        }
    }

    //Start the 25min timer
    fun startWorkingTimer(){

            workingTimer = object : CountDownTimer(6100,1000){
                override fun onTick(millisUntilFinished: Long) {
                    binding.timerTV.text = (millisUntilFinished/1000).toString()
                }

                override fun onFinish() {
                    breakTimeTimer()
                }

            }.start()

    }
    //Start the 5min timer
    fun breakTimeTimer(){
        breakTimer = object : CountDownTimer(6000,1000){
            @SuppressLint("ResourceAsColor")
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTV.apply {
                    setTextColor(R.color.green)
                    text = (millisUntilFinished/1000).toString()
                }
            }

            override fun onFinish() {
                startWorkingTimer()
            }

        }.start()
    }
}