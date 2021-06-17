package ipvc.estg.saveqr.ui.addqrupdate

import android.R.attr.bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import ipvc.estg.saveqr.R


class AddQrUpdate : Fragment() {
//pena

//pena3
    companion object {
        fun newInstance() = AddQrUpdate()
    }

    private lateinit var viewModel: AddQrUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FirebaseApp.initializeApp(this.context)


        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE,
                FirebaseVisionBarcode.FORMAT_AZTEC
            )
            .build()
      //  val image = FirebaseVisionImage.fromBitmap(bitmap)


        return inflater.inflate(R.layout.add_qr_update_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddQrUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
