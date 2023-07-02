package jp.kanoyastore.hiroto.ugajin.actiongame2

import android.graphics.Rect
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import jp.kanoyastore.hiroto.ugajin.actiongame2.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var numberCardY: Float = 0f
    private var cardNumber: Int = 0
    private var scoreCount: Int = 0
    private var isCollisionEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val frameLayout = binding.frameLayout
        val numberCard = binding.numberCard
        val scoreBoard = binding.scoreBoard
        val image1 = binding.image1
        val image2 = binding.image2
        val image3 = binding.image3
        val button1 = binding.button1
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        val button5 = binding.button5
        val button6 = binding.button6

        scoreBoard.text = scoreCount.toString()

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
                checkCollision()
            }
        }
        timer.scheduleAtFixedRate(task2, 0, 16)

        val moveRight = 100
        val moveLeft = -100// 移動距離（ピクセル単位）

        button1.setOnClickListener {
            val currentX = image1.translationX // 現在のX座標
            val newX = currentX + moveLeft // 移動後のX座標
            image1.translationX = newX // ImageViewのX座標を更新

            val minLimit = 0f // 画面の左端の制限
            val maxLimit = binding.root.width - image1.width // 画面の右端の制限

            // 移動後のX座標を制限範囲内に修正
            val clampedX = newX.coerceIn(minLimit, maxLimit.toFloat())
            image1.translationX = clampedX // ImageViewのX座標を更新
        }

        button2.setOnClickListener {
            val currentX = image1.translationX // 現在のX座標
            val newX = currentX + moveRight // 移動後のX座標
            image1.translationX = newX // ImageViewのX座標を更新

            val minLimit = 0f // 画面の左端の制限
            val maxLimit = binding.root.width - image1.width // 画面の右端の制限

            // 移動後のX座標を制限範囲内に修正
            val clampedX = newX.coerceIn(minLimit, maxLimit.toFloat())
            image1.translationX = clampedX // ImageViewのX座標を更新
        }
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
        cardNumber = number
        numberCard.y = 0f
        isCollisionEnabled = true
        numberCard.visibility = View.VISIBLE
    }

    private fun dropNumberCard() {
        val numberCard = binding.numberCard
        numberCard.y += 16f
    }

    val collisionThreshold: Int = 30 // 衝突判定のしきい値（ピクセル）

    private fun checkCollision() {
        val numberCard = binding.numberCard
        val image1 = binding.image1
        val scoreBoard = binding.scoreBoard
        val numberCardRect = Rect()
        numberCard.getGlobalVisibleRect(numberCardRect) // squareViewの位置とサイズを取得

        val image1Rect = Rect()
        image1.getGlobalVisibleRect(image1Rect) // image1の位置とサイズを取得

        // 衝突判定
        if (Rect.intersects(numberCardRect, image1Rect)) {
            // 衝突した場合の処理をここに記述します
            if (isCollisionEnabled) {
                if (cardNumber % 2 == 0) {
                    scoreCount += 2

                    Log.d("Collision", "ピンポーン☺️")
                } else {
                    scoreCount += -1
                    Log.d("Collision", "ブー")
                }
            }
            scoreBoard.text = scoreCount.toString()
            isCollisionEnabled = false
            numberCard.visibility = View.INVISIBLE
        }
    }
}