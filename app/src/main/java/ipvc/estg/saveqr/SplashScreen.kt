package ipvc.estg.saveqr

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.saveqr.ui.registar.RegistarFragment

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //   val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val qr = findViewById<ImageView>(R.id.qr)
        val notConnected = intent.getBooleanExtra(
            ConnectivityManager
                .EXTRA_NO_CONNECTIVITY, false
        )
        if (notConnected != true) {

            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, SPLASH_TIME)

        } else {
            Toast.makeText(applicationContext, getString(R.string.s_conx_int), Toast.LENGTH_SHORT)
                .show()
        }
        qr.setOnClickListener {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            intent.putExtra("EXTRA", "Registar")
            startActivity(intent)
// Now start your activity
        }
    }
}
