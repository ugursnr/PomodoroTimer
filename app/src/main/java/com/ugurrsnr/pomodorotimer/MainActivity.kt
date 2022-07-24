package com.ugurrsnr.pomodorotimer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import com.ugurrsnr.pomodorotimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var workingTimer : CountDownTimer? = null
    private var breakTimer : CountDownTimer? = null
    private var cycleCounter : Int = 0
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
    @SuppressLint("SetTextI18n")
    fun startWorkingTimer(){
        val redColor = ContextCompat.getColor(applicationContext,R.color.red)

        binding.currentStateTV.text = "Working Time!"

            workingTimer = object : CountDownTimer(1500000,1000){
                @SuppressLint("ResourceAsColor")
                override fun onTick(millisUntilFinished: Long) {
                    val timeResult =
                        "${(millisUntilFinished / 1000 / 60).toString().padStart(2, '0')}:" +
                                "${(millisUntilFinished / 1000 % 60).toString().padStart(2, '0')} "

                    binding.timerTV.apply {
                        setTextColor(redColor.toString().toInt())
                        text = timeResult
                    }
                }

                override fun onFinish() {
                    breakTimeTimer()
                }

            }.start()

    }
    //Start the 5min timer
    fun breakTimeTimer(){
        val greenColor = ContextCompat.getColor(applicationContext,R.color.green)

        binding.currentStateTV.text = "Break Time..."
        breakTimer = object : CountDownTimer(300000,1000){
            @SuppressLint("ResourceAsColor")
            override fun onTick(millisUntilFinished: Long) {
                val timeResult =
                    "${(millisUntilFinished / 1000 / 60).toString().padStart(2, '0')}:" +
                            "${(millisUntilFinished / 1000 % 60).toString().padStart(2, '0')} "

                binding.timerTV.apply {
                    setTextColor(greenColor.toString().toInt())
                    text = timeResult
                }
            }

            override fun onFinish() {
                startWorkingTimer()
                cycleCounter ++
                binding.cycleCounterTV.text =" Cycle : $cycleCounter"
            }

        }.start()
    }
}