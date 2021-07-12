package ipvc.estg.saveqr.ui.detalhesQR

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.ui.listaqr.ListaQRViewModel
import kotlinx.android.synthetic.main.detalhes_q_r_fragment.*

class DetalhesQR : Fragment() {

    companion object {
        fun newInstance() = DetalhesQR()
    }

    private lateinit var DetalhesQRviewModel: DetalhesQRViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DetalhesQRviewModel= ViewModelProviders.of(this).get(DetalhesQRViewModel::class.java)
        val root = inflater.inflate(R.layout.detalhes_q_r_fragment, container, false)
        DetalhesQRviewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it

        })
        val content = "ola eu sou um qrcode"
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        val idIVQrcode: ImageView=root.findViewById(R.id.idIVQrcode)
        idIVQrcode.setImageBitmap(bitmap)

        return root
    }

}
