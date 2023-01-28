package `in`.disco_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    var disco_iv:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        disco_iv=findViewById(R.id.disco_iv) as ImageView

        Glide.with(this)
            .load(R.drawable.disco_ball)
            .into(disco_iv!!)

        Handler(Looper.getMainLooper()).postDelayed({
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            finish()
                                                    },2000)
    }
}