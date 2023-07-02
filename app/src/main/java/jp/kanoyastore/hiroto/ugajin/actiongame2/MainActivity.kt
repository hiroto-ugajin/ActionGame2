package jp.kanoyastore.hiroto.ugajin.actiongame2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import jp.kanoyastore.hiroto.ugajin.actiongame2.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var numberCardY: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val frameLayout = binding.frameLayout
        val numberCard = binding.numberCard
        val image1 = binding.image1
        val image2 = binding.image2
        val image3 = binding.image3
        val button1 = binding.button1
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        val button5 = binding.button5
        val button6 = binding.button6

        val timer = Timer()

        val task = timerTask {
            runOnUiThread {
                setNumberCard()
            }
        }
        timer.scheduleAtFixedRate(task, 0, 3000)

        val task2 = timerTask {
            runOnUiThread {
                dropNumberCard()
            }
        }
        timer.scheduleAtFixedRate(task2, 0, 16)
    }

    private fun setNumberCard() {
        val numberCard = binding.numberCard
        val random = Random()
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val startX = random.nextInt(screenWidth - 100) // 画面の幅内でランダムなX軸位置を取得
        numberCard.x = startX.toFloat()
        val number = random.nextInt(100)
        numberCard.text = number.toString()
        numberCard.y = 0f
    }

    private fun dropNumberCard() {
        val numberCard = binding.numberCard
        numberCard.y += 16f
    }
}