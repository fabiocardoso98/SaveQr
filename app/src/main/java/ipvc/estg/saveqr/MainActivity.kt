package ipvc.estg.saveqr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import ipvc.estg.saveqr.api.ServiceBuilder
import ipvc.estg.saveqr.api.endpoints.usersEndpoint
import ipvc.estg.saveqr.api.models.Users
import ipvc.estg.saveqr.api.models.UsersReturn
import ipvc.estg.saveqr.ui.listapasta.ListaPastaFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



//12131




        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_listapasta), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val frag = intent.getStringExtra("EXTRA")
        if (frag == "Listar") {
            navController.navigate(R.id.nav_listapasta);
        }

        val request = ServiceBuilder.buildService(usersEndpoint::class.java)
        val call = request.getUsers()

        call.enqueue(object : Callback<UsersReturn> {
            override fun onResponse(call: Call<UsersReturn>, response: Response<UsersReturn>) {
                //Toast.makeText(applicationContext, response.body()!!.msg, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<UsersReturn>, t: Throwable) {
                Toast.makeText(applicationContext, "DEU ERRO", Toast.LENGTH_LONG).show()
                Log.d("ENDPONT", t.toString())
            }
        })

    }





override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
    }
}
