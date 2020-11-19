package android.flor.notasdroidapp_flor.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.flor.notasdroidapp_flor.LoginActivity
import android.flor.notasdroidapp_flor.PrincipalActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.flor.notasdroidapp_flor.R
import android.flor.notasdroidapp_flor.controladorDB.AlumnoDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.flor.notasdroidapp_flor.ui.inicio.InicioViewModel
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.fragment_ajustes.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlin.system.exitProcess

class CerrarSesionFragment : Fragment() {

    private var backPressedTime: Long = 0
    lateinit var backToast: Toast

    private lateinit var inicioViewModel: InicioViewModel

    lateinit var alumno: Alumno
    lateinit var alumnosDBHelper: AlumnoDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialogo1: AlertDialog.Builder = AlertDialog.Builder(activity)
        dialogo1.setTitle("Cerrar Sesión")
        //dialogo1.setMessage("¿Desea Cerrar Sesión?")
        dialogo1.setCancelable(false)
        dialogo1.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener { dialogo1, id -> aceptar() })
        dialogo1.setNegativeButton("Cancelar",
            DialogInterface.OnClickListener { dialogo1, id -> cancelar() })
        dialogo1.show()

        inicioViewModel =
            ViewModelProviders.of(this).get(InicioViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_inicio, container, false)
        val textView: TextView = root.findViewById(R.id.nav_inicio)
        inicioViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        alumno = (activity as PrincipalActivity?)!!.alumno

        return root

    }

    fun aceptar() {
        try {
            Toast.makeText(activity, "Aceptar.", Toast.LENGTH_SHORT)
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

            // Deslogueamos al usuario
            alumnosDBHelper = AlumnoDBHelper(requireContext())

            alumno = Alumno(alumno.idalumno, alumno.nombre, alumno.email, alumno.contrasenia, alumno.ciclo, 0)

            if (alumnosDBHelper.updateAlumno(alumno)) {
                Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Se ha producido un Error", Toast.LENGTH_SHORT).show()
        }

    }

    fun cancelar() {
        Toast.makeText(activity, "Cancelar.", Toast.LENGTH_SHORT)
    }

}