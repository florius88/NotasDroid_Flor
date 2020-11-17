package android.flor.notasdroidapp_flor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private var backPressedTime:Long = 0
    lateinit var backToast:Toast

    //Funcion para construir la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onBackPressed() {
        backToast = Toast.makeText(this, "Presione de nuevo para salir de la aplicación.", Toast.LENGTH_LONG)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            finishAffinity()
            exitProcess(0)
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    fun entrar(view: View) {
        val intent = Intent(this, PrincipalActivity::class.java).apply {
        }
        startActivity(intent)
    }

    fun registrarse(view: View) {
        val intent = Intent(this, RegistroActivity::class.java).apply {
        }
        startActivity(intent)
    }
}