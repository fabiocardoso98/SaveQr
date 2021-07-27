package ipvc.estg.saveqr.ui.detalhesQR

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.QrCodesEndpoint
import ipvc.estg.saveqr.api.models.QrCodeReturn
import kotlinx.android.synthetic.main.item_list_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetalhesQR : Fragment() {

    companion object {
        fun newInstance() = DetalhesQR()
    }

    private lateinit var DetalhesQRviewModel: DetalhesQRViewModel
    var qrcontent=""

    var idIVQrcode: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DetalhesQRviewModel= ViewModelProviders.of(this).get(DetalhesQRViewModel::class.java)
        val root = inflater.inflate(R.layout.detalhes_q_r_fragment, container, false)
        DetalhesQRviewModel.text.observe(viewLifecycleOwner, Observer {
            //     textView.text = it

        })
        idIVQrcode=root.findViewById(R.id.idIVQrcode)
        getQr()
        return root
    }
    fun getQr() {
        val request = ServiceBuilder.buildService(QrCodesEndpoint::class.java)
        val QrId = this.arguments?.getInt("QrId")
        val call = request.getQrCodeById(QrId)
        call.enqueue(object : Callback<QrCodeReturn> {
            override fun onResponse(call: Call<QrCodeReturn>, response: Response<QrCodeReturn>) {
                if (response.code() == 200) {
                    val qr = response.body()!!
                    val content = qr.data.content
                    val writer = QRCodeWriter()
                    val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until width) {
                        for (y in 0 until height) {
                            bitmap.setPixel(
                                x,
                                y,
                                if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                            )
                        }
                    }
                    idIVQrcode?.setImageBitmap(bitmap)

                }
                idIVQrcode?.setOnClickListener {
                    val qr = response.body()!!
                    val uri: Uri =
                        Uri.parse( qr.data.content) // missing 'http://' will cause crashed
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

            }

            override fun onFailure(call: Call<QrCodeReturn>, t: Throwable) {
                Toast.makeText(activity, "DEU ERRO", Toast.LENGTH_LONG).show()

            }
        })
    }

}
