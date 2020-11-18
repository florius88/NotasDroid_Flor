package android.flor.notasdroidapp_flor

import android.content.Intent
import android.flor.notasdroidapp_flor.controladorDB.*
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var alumnosDBHelper: AlumnoDBHelper
    lateinit var asigDBHelper: AsignaturaDBHelper
    lateinit var cicloDBHelper: CicloDBHelper
    lateinit var relacionDBHelper: Ciclo_AsignaturaDBHelper
    lateinit var pruebaDBHelper: PruebaDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        //Le damos tiempo
        Thread.sleep(3000)

        //Llamamos al tema creado para la splash
        setTheme(R.style.AppTheme)

//        //Validar login
//        Toast.makeText(this, "Comprobando Login", Toast.LENGTH_SHORT).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BBDD
        cargarBBDD()

        //Validar login
        var alumno = alumnosDBHelper.selectAlumnoLog()

        if (alumno.isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            //Una vez terminada la splash, y comprobado el login nos lleva a la pagina de Principal
            val intent = Intent(this, PrincipalActivity::class.java)
            intent.putExtra("Email", alumno[0].email)
            startActivity(intent)
        }
    }

    fun cargarBBDD(): Unit {

        // Tabla Alumnos
        alumnosDBHelper = AlumnoDBHelper(this)
//
//        var nombre = "flor"
//        var email = "flor@flor.com"
//        var contrasenia = "1234"
//        var ciclo = 1
//        var result = alumnosDBHelper.insertAlumno(Alumno(0, nombre, email, contrasenia, ciclo, 0))

//        var alumnos = alumnosDBHelper.selectAllAlumnos()

        // Tabla Ciclos
        cicloDBHelper = CicloDBHelper(this)
        cicloDBHelper.insertarTodosCiclos()
//        var ciclos = cicloDBHelper.selectAllCiclos()

        // Tabla Asignaturas
        asigDBHelper = AsignaturaDBHelper(this)
        asigDBHelper.insertarTodasAsignaturas()
//        var asig = asigDBHelper.selectAllAsignaturas()

        // Tabla Ciclo_Asignatura
        relacionDBHelper = Ciclo_AsignaturaDBHelper(this)
        relacionDBHelper.insertarTodasRelaciones()
//        var relaciones = relacionDBHelper.selectAllCicloAsignaturas()

        // Tabla Pruebas
        pruebaDBHelper = PruebaDBHelper(this)
//        var pruebas = pruebaDBHelper.selectAllPruebas()
    }


}