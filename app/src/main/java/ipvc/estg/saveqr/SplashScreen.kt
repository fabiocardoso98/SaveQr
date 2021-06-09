package ipvc.estg.saveqr

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {


    private val SPLASH_TIME: Long = 1500
    private var registo=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
           val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val qr = findViewById<ImageView>(R.id.qr)
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (isConnected == true) {

            Handler().postDelayed({
                if (registo==0)
                startActivity(Intent(this, MainActivity::class.java))
                // close this activity
                finish()

            }, SPLASH_TIME)
            qr.setOnClickListener {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                intent.putExtra("EXTRA", "Registar")
                startActivity(intent)
                registo=1;
            }
        } else {
            Toast.makeText(applicationContext, getString(R.string.s_conx_int), Toast.LENGTH_SHORT)
                .show()
        }

    }
}
