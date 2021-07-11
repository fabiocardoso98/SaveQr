package ipvc.estg.saveqr

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.Toast
import androidx.core.graphics.ColorUtils
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.api.endpoints.foldersEndpoint
import ipvc.estg.saveqr.api.api.models.Folders
import ipvc.estg.saveqr.api.api.models.FoldersReturn
import ipvc.estg.saveqr.ui.listapasta.TITULO
import kotlinx.android.synthetic.main.fragment_listapasta.*
import kotlinx.android.synthetic.main.listapasta_item.*
import kotlinx.android.synthetic.main.popup_addpasta.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class popup_insertPasta : AppCompatActivity() {
    var idUser: Int = 0

    private lateinit var nomePasta: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.popup_addpasta)

        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(animator.animatedValue as Int)
        }


        popup_window_background.alpha = 0f
        popup_window_background.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        outPop.setOnClickListener {
            onBackPressed()
        }

        val id: Int? = intent.getIntExtra("id",0)
        val loginShared: SharedPreferences = getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
        idUser = loginShared.getInt(getString(R.string.idLogin), 0)


            nomePasta = findViewById(R.id.email)
            val name: String? = intent.getStringExtra("name").toString()
            nomePasta.setText(name)

        addpasta.setOnClickListener {
            updateReport()
        }

    }

    private fun updateReport() {
        nomePasta = findViewById(R.id.email)

        val id: Int? = intent.getIntExtra("id", 0)


        if (!TextUtils.isEmpty(nomePasta.text)) {
            val request = ServiceBuilder.buildService(foldersEndpoint::class.java)

            val call = request.setUpdateFolders(id!!,nomePasta.text.toString())

            call.enqueue(object  : Callback<FoldersReturn> {
                override fun onResponse(call: Call<FoldersReturn>, response: Response<FoldersReturn>) {
                    if(response.isSuccessful) {

                        finish()


                    }
                }

                override fun onFailure(call: Call<FoldersReturn>, t: Throwable) {
                    finish()
                }
            })

        } else {
            Toast.makeText(applicationContext, "Campos vazios", Toast.LENGTH_LONG).show()
        }


    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        popup_window_background.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }



}