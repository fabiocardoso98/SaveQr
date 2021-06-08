package ipvc.estg.saveqr.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import ipvc.estg.saveqr.R

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val registar: TextView = root.findViewById(R.id.registar)
        val enter: ImageView = root.findViewById(R.id.add)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
          //  textView.text = it
        })

            enter.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_listapasta, null))

            registar.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_registar, null))



     /*   enter.setOnClickListener {
            val intent = Intent(this, fragment_registar::class.java)
            startActivity(intent)
        }*/
        return root

    }

}
