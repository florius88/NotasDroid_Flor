package android.flor.notasdroidapp_flor.ui.mi_expediente

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
import android.flor.notasdroidapp_flor.controladorDB.CicloDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.widget.ArrayAdapter
import android.widget.Spinner

class MiExpedienteFragment : Fragment(), IOnBackPressed {

    private lateinit var miExpedienteViewModel: MiExpedienteViewModel

    lateinit var alumno: Alumno
    lateinit var alumnosDBHelper: AlumnoDBHelper
    lateinit var cicloDBHelper: CicloDBHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        miExpedienteViewModel =
            ViewModelProviders.of(this).get(MiExpedienteViewModel::class.java)
          val root = inflater.inflate(R.layout.fragment_mi_expediente, container, false)
//        val textView: TextView = root.findViewById(R.id.nav_miExpediente)
//        miExpedienteViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        alumno = (activity as PrincipalActivity?)!!.alumno
        cicloDBHelper = CicloDBHelper(requireContext())

        var ciclos = cicloDBHelper.selectCiclo(alumno.ciclo)
        var cic = ciclos[0]

        // Introducimos los datos del alumno
        val ex_nombre: TextView = root.findViewById(R.id.textViewExpedienteNombre)
        ex_nombre.text = alumno.nombre

        val ex_email: TextView = root.findViewById(R.id.textViewExpedienteEmail)
        ex_email.text = alumno.email

        val ex_ciclo: TextView = root.findViewById(R.id.textViewExpedienteCiclo)
        ex_ciclo.text = cic.siglas

        val ex_curso: TextView = root.findViewById(R.id.textViewExpedienteCurso)
        ex_curso.text = cic.curso.toString()

        // ------------------------------------------------------------------------------------------------------
//        val ex_notaMedia: TextView = root.findViewById(R.id.editTextAjustesContrasenia)
//        ex_notaMedia.text =

        return root
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}