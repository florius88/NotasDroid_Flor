package android.flor.notasdroidapp_flor

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.flor.notasdroidapp_flor.controladorDB.AlumnoDBHelper
import android.flor.notasdroidapp_flor.controladorDB.CicloDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.modelo.Ciclo
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {
    lateinit var alumnosDBHelper: AlumnoDBHelper
    lateinit var cicloDBHelper: CicloDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        alumnosDBHelper = AlumnoDBHelper(this)
        cicloDBHelper = CicloDBHelper(this)

        //SPINNER CICLO
        val listaCiclos = cicloDBHelper.selectCicloByCurso(1)

        val spinnerCiclo = findViewById<Spinner>(R.id.spinnerRegistroCiclo)
        if (spinnerCiclo != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, listaCiclos
            )
            spinnerCiclo.adapter = adapter
        }

        //SPINNER CURSO
        val listaCursos = ArrayList<String>()
        listaCursos.add("1")
        listaCursos.add("2")

        val spinnerCurso = findViewById<Spinner>(R.id.spinnerRegistroCurso)
        if (spinnerCurso != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, listaCursos
            )
            spinnerCurso.adapter = adapter
        }

    } // fin del onCreate


    //Funcion onClick del boton Registrarse
    fun registrarse(view: View) {
        try {

            alumnosDBHelper = AlumnoDBHelper(this)
            cicloDBHelper = CicloDBHelper(this)

            var alumno: Alumno
            var nombre: String
            var email: String
            var contrasenia: String
            var comproContrasenia: String
            var ciclo: String
            var curso: String
            var idciclo: Int

            nombre = editTextRegistroNombre.text.toString()
            email = editTextRegistroEmail.text.toString()
            ciclo = spinnerRegistroCiclo.selectedItem.toString()
            curso = spinnerRegistroCurso.selectedItem.toString()
            contrasenia = editTextRegistroContrasenia.text.toString()
            comproContrasenia = editTextRegistroConfirmarContrasenia.text.toString()

            if (nombre.equals("") || email.equals("") || ciclo.equals("") || contrasenia.equals("") || comproContrasenia.equals(
                    ""
                )
                || curso.equals("")
            ) {

                Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show()

            } else {
                var alumnos = alumnosDBHelper.selectAlumnoEmail(email)

                // Si no devuelve alumnos, creamos uno nuevo
                if (alumnos.isEmpty()) {
                    // Si las contrasenias coinciden...
                    if (contrasenia.equals(comproContrasenia)) {
                        // Buscamos el id del ciclo
                        var ciclos = cicloDBHelper.selectCicloByCursoNombre(ciclo, Integer.parseInt(curso))
                        idciclo = ciclos[0].idciclo

                        alumno = Alumno(0, nombre, email, contrasenia, idciclo, 0)

                        alumnosDBHelper.insertAlumno(alumno)

                        Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java).apply {
                        }
                        startActivity(intent)


                    } else {
                        Toast.makeText(this, "Las contraseñas no coindicen", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()

                }
            }
        } catch (e: Exception){
            Toast.makeText(this, "Se ha producido un Error", Toast.LENGTH_SHORT).show()
        }
    }
}