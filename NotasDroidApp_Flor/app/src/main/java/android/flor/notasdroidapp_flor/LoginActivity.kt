package android.flor.notasdroidapp_flor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    //Funcion para construir la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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