package android.flor.notasdroidapp_flor

import android.content.Intent
import android.flor.notasdroidapp_flor.controladorDB.AlumnoDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.util.ArrayList
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {
    lateinit var alumnosDBHelper: AlumnoDBHelper

    private var backPressedTime: Long = 0
    lateinit var backToast: Toast

    //Funcion para construir la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    //Funcion para salir de la App
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

    // Funcion onClick del boton Entrar
    fun entrar(view: View) {
        try {


            alumnosDBHelper = AlumnoDBHelper(this)
            var alumno: Alumno
            var email: String
            var contrasenia: String

            email = editTextTextEmailAddressLogin.text.toString()
            contrasenia = editTextTextPasswordLogin.text.toString()

            if (email.equals("") || contrasenia.equals("")) {
                Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show()

            } else {

                var alumnos = alumnosDBHelper.selectAlumnoEmail(email)

                if (alumnos.isEmpty()) {
                    Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show()
                } else {
                    alumno = alumnos[0]
                    if (alumno.contrasenia.equals(contrasenia)) {
                        alumno.logueado = 1
                        alumnosDBHelper.updateAlumno(alumno)

                        val intent = Intent(this, PrincipalActivity::class.java).apply {
                        }
                        intent.putExtra("Email", alumno.email)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Se ha producido un Error", Toast.LENGTH_SHORT).show()
        }
    }


    //Funcion onClick boton Registrarse
    fun registrarse(view: View) {
        val intent = Intent(this, RegistroActivity::class.java).apply {
        }
        startActivity(intent)
    }
}