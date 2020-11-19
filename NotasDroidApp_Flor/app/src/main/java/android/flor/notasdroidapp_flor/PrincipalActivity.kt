package android.flor.notasdroidapp_flor

import android.content.Intent
import android.flor.notasdroidapp_flor.controladorDB.AlumnoDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.modelo.Prueba
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class PrincipalActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    public var isClicEventoFila = true
    public var isClicEventoFilaPrueba = true
    public lateinit var pruebaActual: Prueba
    var email: String? = ""
    lateinit var alumno: Alumno
    lateinit var alumnosDBHelper: AlumnoDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val objetoIntent: Intent = intent
        email = objetoIntent.getStringExtra("Email")

        if (null != email) {
            val mail = email
            if (null != mail) {
                alumnosDBHelper = AlumnoDBHelper(this)
                var alumnos = alumnosDBHelper.selectAlumnoEmail(mail)
                alumno = alumnos[0]
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_miMatricula, R.id.nav_miExpediente, R.id.nav_ajustes, R.id.nav_salir
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerView = navView.getHeaderView(0)
        val nav_email: TextView = headerView.findViewById(R.id.textViewNavEmail)
        nav_email.text = email

        val nav_nombre: TextView = headerView.findViewById(R.id.textViewNavNombre)
        nav_nombre.text = alumno.nombre

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Funcion que captura el boton de retroceder del movil
    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let { isCanceled: Boolean ->
                    //Toast.makeText(this, "onBackpressed " + isCanceled, Toast.LENGTH_SHORT).show()
                    if (!isCanceled) {
                        super.onBackPressed()
                    }
                }
            }
        }
    }
}