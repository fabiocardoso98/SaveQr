package ipvc.estg.saveqr.ui.lerQr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import ipvc.estg.saveqr.R


class LerQrActivity : AppCompatActivity() {

    private var btnScanner: Button? = null
    private var tvBarCode: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ler_qr)
        val btnScanner = findViewById<Button>(R.id.btnScanner)
        tvBarCode = findViewById<TextView>(R.id.tvBarCode)

        btnScanner.setOnClickListener(mOnClickListener)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) if (result.contents != null) {
            tvBarCode!!.text = """
            El código de barras es:
            ${result.contents}
            """.trimIndent()
        } else {
            tvBarCode!!.text = "Error al escanear el código de barras"
        }
    }

    private val mOnClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            when (v.getId()) {
                R.id.btnScanner -> IntentIntegrator(this@LerQrActivity).initiateScan()
            }
        }
    }
}