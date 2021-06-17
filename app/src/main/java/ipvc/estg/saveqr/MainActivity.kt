package ipvc.estg.saveqr

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
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
import ipvc.estg.saveqr.ui.listapasta.ListaPastaViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

            //   val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //       .requestEmail()
        //      .build()

        // Build a GoogleSignInClient with the options specified by gso.
        //    val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)




        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setNavigationItemSelectedListener(this)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_listapasta,R.id.nav_settings,R.id.nav_logout), drawerLayout)


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val toogle = ActionBarDrawerToggle(
            this,drawerLayout,toolbar,0,0
        )
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        navView.setNavigationItemSelectedListener (this)
        val frag = intent.getStringExtra("EXTRA")
        if (frag == "Listar") {
           // navController.navigate(R.id.nav_listapasta);
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
    override fun onNavigationItemSelected(item: MenuItem):Boolean{

        when(item.itemId){
            R.id.nav_logout -> {
                val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.login_p), Context.MODE_PRIVATE
                )
                with(sharedPref.edit()){
                    putBoolean(getString(R.string.status), false)
                    putString(getString(R.string.userLogin), "")
                    putInt(getString(R.string.idLogin), 0)
                    commit()
                }
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_scanqr -> {
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.nav_addQrUpdate);
            }
            R.id.nav_listapasta ->{
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.nav_listapasta);
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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
