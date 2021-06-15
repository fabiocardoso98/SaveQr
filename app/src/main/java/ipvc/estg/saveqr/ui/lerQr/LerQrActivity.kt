package ipvc.estg.saveqr.ui.lerQr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.zxing.Result
import ipvc.estg.saveqr.History
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.mvp.BaseMvpActivity
import ipvc.estg.saveqr.ui.LerQr.LerQrActivityContract
import ipvc.estg.saveqr.ui.data.ORM.HistoryORM
import kotlinx.android.synthetic.main.activity_ler_qr.*
import kotlinx.android.synthetic.main.dialog_scan_success.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
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

    }

    override fun showSuccessScanningDialog(result: String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scan_success, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mDialogView.tvResult.text = result
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
}