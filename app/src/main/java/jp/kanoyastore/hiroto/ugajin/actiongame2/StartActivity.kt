package jp.kanoyastore.hiroto.ugajin.actiongame2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.kanoyastore.hiroto.ugajin.actiongame2.databinding.ActivityMainBinding
import jp.kanoyastore.hiroto.ugajin.actiongame2.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.image.setImageResource(R.drawable.start200)

        binding.start.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}