package ipvc.estg.saveqr.ui.lerQr

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import ipvc.estg.saveqr.History
import ipvc.estg.saveqr.MainActivity
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.QrCodesEndpoint
import ipvc.estg.saveqr.api.models.Qrcodes
import ipvc.estg.saveqr.mvp.BaseMvpActivity
import ipvc.estg.saveqr.ui.LerQr.LerQrActivityContract
import ipvc.estg.saveqr.ui.data.ORM.HistoryORM
import kotlinx.android.synthetic.main.activity_ler_qr.*
import kotlinx.android.synthetic.main.dialog_scan_success.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class LerQrActivity : BaseMvpActivity<LerQrActivityContract.View, LerQrActivityContract.Presenter>(),
    LerQrActivityContract.View, View.OnClickListener, ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    private var flashState: Boolean = false
    private var dialog: AlertDialog? = null
    private var mHistoryOrm: HistoryORM? = null

    override var mPresenter: LerQrActivityContract.Presenter = LerQrActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ler_qr)

        //Verificar permiss
        checkPermission(Manifest.permission.CAMERA,401)

       mHistoryOrm = HistoryORM()
        initUI()
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    private fun initUI() {
        mScannerView = ZXingScannerView(this)
        frmContent.addView(mScannerView)
        btnLight.setOnClickListener(this)
        btnHistory.setOnClickListener(this)
        privacyPolicyTextView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnLight -> {
                if(flashState) {
                    v.setBackgroundResource(R.mipmap.ic_flash_on)
                    showMessage(R.string.flashlight_turned_off)
                    mScannerView?.setFlash(false)
                    flashState = false
                }else {
                    v.setBackgroundResource(R.mipmap.ic_flash_off)
                    showMessage(R.string.flashlight_turned_on)
                    mScannerView?.setFlash(true)
                    flashState = true
                }
            }
            R.id.btnHistory -> {
              //  startActivity(Intent(this, HistoryActivity::class.java))
            }
            R.id.privacyPolicyTextView -> {
              //  val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SyncStateContract.Constants.privacyPolicy))
               // startActivity(browserIntent)
            }
        }
    }

    override fun handleResult(result: Result?) {
        var history: History = History(
            DateFormat.getDateTimeInstance().format(Calendar.getInstance().time),
            result?.text.toString())
        mHistoryOrm?.add(this, history)
        mPresenter.qrCodeScanned(history)
        val intent = Intent(this@LerQrActivity, MainActivity::class.java)
        val request = ServiceBuilder.buildService(QrCodesEndpoint::class.java)
        val call = request.postQrCode(
            "qr exemplo",
            result?.text.toString(),
            5,
            5,
            405
        )
        call.enqueue(object : Callback<Qrcodes> {
            override fun onResponse(
                call: Call<Qrcodes>,
                response: Response<Qrcodes>
            ) {


                if (response.isSuccessful) {
                    // Log.d("***","Sucesso")
                    Toast.makeText(this@LerQrActivity, "Sucesso!", Toast.LENGTH_SHORT)
                        .show();
                    startActivity(intent)
                    finish()
                    // communicator.passDataconn(root.nome.text.toString())
                    //  communicator.passDataconn(id,root.nome.text.toString(),root.username.text.toString(),root.email.text.toString(),root.password.text.toString())
                    // getActivity()?.getSupportFragmentManager()?.beginTransaction().remove(this@RegistarFragment).commit();


                }

            }

            override fun onFailure(call: Call<Qrcodes>, t: Throwable) {
                Toast.makeText(this@LerQrActivity, "Erro, tente mais tarde!", Toast.LENGTH_SHORT).show();
            }
        })
    }

    override fun showSuccessScanningDialog(result: String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scan_success, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //mDialogView.tvResult.text = result
        mDialogView.btnSearch.setOnClickListener {
            mPresenter.searchByResultBtnPressed(result)
        }
        mDialogView.btnCopy.setOnClickListener {
            mPresenter.copyResultBtnPressed(result)
        }
        mDialogView.btnShare.setOnClickListener {
            mPresenter.shareResultBtnPressed(result)
        }
        dialog = dialogBuilder.create()
        dialog?.setOnCancelListener {
            continueScanning()
        }
        dialog?.show()
    }

    override fun continueScanning() {
        dialog?.dismiss()
        mScannerView?.resumeCameraPreview(this)
    }

    //Pedir premissao camara
    //https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@LerQrActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
           // Toast.makeText(this@LerQrActivity, "Without Permission", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this@LerQrActivity, arrayOf(permission), requestCode)
        } else {
          //  Toast.makeText(this@LerQrActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }
}