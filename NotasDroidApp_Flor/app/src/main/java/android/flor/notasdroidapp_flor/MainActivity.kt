package android.flor.notasdroidapp_flor

import android.content.Intent
import android.flor.notasdroidapp_flor.modelo.AppBaseDatos
import android.flor.notasdroidapp_flor.modelo.Asignatura
import android.flor.notasdroidapp_flor.modelo.UserModel
import android.flor.notasdroidapp_flor.modelo.UsersDBHelper
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        //Le damos tiempo
        Thread.sleep(3000)

        //Llamamos al tema creado para la splash
        setTheme(R.style.AppTheme)

        //Validar login
        Toast.makeText(this, "Comprobando Login", Toast.LENGTH_SHORT).show()

//        var listaAsignaturas = emptyList<Asignatura>()
//
//        val basedatos = AppBaseDatos.getBasedatos(this)
//
//        basedatos.asignatura().obtenerTodas().observe(this, Observer {
//            listaAsignaturas = it
//        })

//        val context = this
//        val db = DataBaseHandler(context)
//
//        val data = db.readData()

        usersDBHelper = UsersDBHelper(this)

        var userid = "1"
        var name = "pepe"
        var age = "30"
        var result = usersDBHelper.insertUser(UserModel(userid = userid,name = name,age = age))

        var users = usersDBHelper.readAllUsers()




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //Una vez terminada la splash, nos lleva a la pagina de login

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }


}