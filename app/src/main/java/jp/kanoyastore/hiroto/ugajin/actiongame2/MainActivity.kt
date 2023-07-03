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
    private var maximumScore: Int = 0
    private var prospectiveScore: Int = 0
    private var isCollisionEnabled = true
    private var isCollision2Enabled = true
    private var isCollision3Enabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val frameLayout = binding.frameLayout
        val numberCard = binding.numberCard
        val scoreBoard = binding.scoreBoard
        val maximumScoreBoard = binding.maximumScoreBoard
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
                calcMaximumScore()
            }
        }
        timer.scheduleAtFixedRate(task, 0, 5000)

        val task2 = timerTask {
            runOnUiThread {
                dropNumberCard()
                checkCollision()
                checkCollision2()
                checkCollision3()
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

        button3.setOnClickListener {
            val currentX = image2.translationX // 現在のX座標
            val newX = currentX + moveLeft // 移動後のX座標
            image2.translationX = newX // ImageViewのX座標を更新

            val minLimit = 0f // 画面の左端の制限
            val maxLimit = binding.root.width - image2.width // 画面の右端の制限

            // 移動後のX座標を制限範囲内に修正
            val clampedX = newX.coerceIn(minLimit, maxLimit.toFloat())
            image2.translationX = clampedX // ImageViewのX座標を更新
        }

        button4.setOnClickListener {
            val currentX = image2.translationX // 現在のX座標
            val newX = currentX + moveRight // 移動後のX座標
            image2.translationX = newX // ImageViewのX座標を更新

            val minLimit = 0f // 画面の左端の制限
            val maxLimit = binding.root.width - image2.width // 画面の右端の制限

            // 移動後のX座標を制限範囲内に修正
            val clampedX = newX.coerceIn(minLimit, maxLimit.toFloat())
            image2.translationX = clampedX // ImageViewのX座標を更新
        }

        button5.setOnClickListener {
            val currentX = image3.translationX // 現在のX座標
            val newX = currentX + moveLeft // 移動後のX座標
            image3.translationX = newX // ImageViewのX座標を更新

            val minLimit = 0f // 画面の左端の制限
            val maxLimit = binding.root.width - image3.width // 画面の右端の制限

            // 移動後のX座標を制限範囲内に修正
            val clampedX = newX.coerceIn(minLimit, maxLimit.toFloat())
            image3.translationX = clampedX // ImageViewのX座標を更新
        }

        button6.setOnClickListener {
            val currentX = image3.translationX // 現在のX座標
            val newX = currentX + moveRight // 移動後のX座標
            image3.translationX = newX // ImageViewのX座標を更新

            val minLimit = 0f // 画面の左端の制限
            val maxLimit = binding.root.width - image3.width // 画面の右端の制限

            // 移動後のX座標を制限範囲内に修正
            val clampedX = newX.coerceIn(minLimit, maxLimit.toFloat())
            image3.translationX = clampedX // ImageViewのX座標を更新
        }
    }

    private fun setNumberCard() {
        val numberCard = binding.numberCard
        val maximumScoreBoard = binding.maximumScoreBoard
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
        isCollision2Enabled = true
        isCollision3Enabled = true
        numberCard.visibility = View.VISIBLE
    }

    private fun dropNumberCard() {
        val numberCard = binding.numberCard
        numberCard.y += 5f
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
                } else {
                    scoreCount += -1
                }
            }
            scoreBoard.text = scoreCount.toString()
            isCollisionEnabled = false
            isCollision2Enabled = false
            isCollision3Enabled = false
            numberCard.visibility = View.INVISIBLE
        }
    }

    private fun checkCollision2() {
        val numberCard = binding.numberCard
        val image2 = binding.image2
        val scoreBoard = binding.scoreBoard
        val numberCardRect = Rect()
        numberCard.getGlobalVisibleRect(numberCardRect) // squareViewの位置とサイズを取得
        val image2Rect = Rect()
        image2.getGlobalVisibleRect(image2Rect) // image1の位置とサイズを取得
        // 衝突判定
        if (Rect.intersects(numberCardRect, image2Rect)) {
            // 衝突した場合の処理をここに記述します
            if (isCollision2Enabled) {
                if (cardNumber % 3 == 0) {
                    scoreCount += 3
                } else {
                    scoreCount += -1
                }
            }
            scoreBoard.text = scoreCount.toString()
            isCollision2Enabled = false
            isCollision3Enabled = false
            numberCard.visibility = View.INVISIBLE
        }
    }

    private fun checkCollision3() {
        val numberCard = binding.numberCard
        val image3 = binding.image3
        val scoreBoard = binding.scoreBoard
        val numberCardRect = Rect()
        numberCard.getGlobalVisibleRect(numberCardRect) // squareViewの位置とサイズを取得
        val image3Rect = Rect()
        image3.getGlobalVisibleRect(image3Rect) // image1の位置とサイズを取得
        // 衝突判定
        if (Rect.intersects(numberCardRect, image3Rect)) {
            // 衝突した場合の処理をここに記述します
            if (isCollision3Enabled) {
                if (cardNumber % 5 == 0) {
                    scoreCount += 2
                } else {
                    scoreCount += -1
                }
            }
            scoreBoard.text = scoreCount.toString()
            isCollision3Enabled = false
            numberCard.visibility = View.INVISIBLE
        }
    }

    private fun calcMaximumScore() {
        val maximumScoreBoard = binding.maximumScoreBoard
        if (cardNumber%3 == 0) { prospectiveScore = 3 }
        else if (cardNumber%2 == 0 || cardNumber%5 == 5) { prospectiveScore = 2}
        else prospectiveScore = 0
        maximumScore = maximumScore + prospectiveScore
        maximumScoreBoard.text = "最大スコア　" + "${maximumScore.toString()}"
    }
}