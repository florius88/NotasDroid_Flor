package android.flor.notasdroidapp_flor.ui.ajustes

import android.flor.notasdroidapp_flor.PrincipalActivity
import android.flor.notasdroidapp_flor.R
import android.flor.notasdroidapp_flor.controladorDB.AlumnoDBHelper
import android.flor.notasdroidapp_flor.controladorDB.CicloDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_ajustes.*

class AjustesFragment : Fragment(), IOnBackPressed {

    private lateinit var ajustesViewModel: AjustesViewModel
    lateinit var alumno: Alumno
    lateinit var alumnosDBHelper: AlumnoDBHelper
    lateinit var cicloDBHelper: CicloDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ajustesViewModel =
            ViewModelProviders.of(this).get(AjustesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ajustes, container, false)
//        val textView: TextView = root.findViewById(R.id.nav_ajustes)
//        ajustesViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val buttonCalculate = root.findViewById(R.id.buttonAjustesActualizar) as Button

        buttonCalculate.setOnClickListener() {
            actualizar()
        }

        alumno = (activity as PrincipalActivity?)!!.alumno
        cicloDBHelper = CicloDBHelper(requireContext())

        var ciclos = cicloDBHelper.selectCiclo(alumno.ciclo)
        var cic = ciclos[0]

        //SPINNER CICLO
        val listaCiclos = cicloDBHelper.selectCicloByCurso(1)

        val spinnerCiclo = root.findViewById<Spinner>(R.id.spinnerAjustesCiclo)
        if (spinnerCiclo != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, listaCiclos
            )
            spinnerCiclo.adapter = adapter
        }

        //SPINNER CURSO
        val listaCursos = ArrayList<String>()
        listaCursos.add("1")
        listaCursos.add("2")

        val spinnerCurso = root.findViewById<Spinner>(R.id.spinnerAjustesCurso)
        if (spinnerCurso != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, listaCursos
            )
            spinnerCurso.adapter = adapter
        }

        // Introducimos los datos del alumno
        val aj_nombre: TextView = root.findViewById(R.id.editTextAjustesNombre)
        aj_nombre.text = alumno.nombre

        val aj_email: TextView = root.findViewById(R.id.editTextAjustesEmail)
        aj_email.text = alumno.email

        //-------------------------------------------------------------------------------------------------------------
        val aj_ciclo: Spinner = root.findViewById(R.id.spinnerAjustesCiclo)

        var opc: Int = 0

        for (it in listaCiclos) {
            if (it.equals(cic.nombre)) {
                break
            }
            opc++
        }

        aj_ciclo.setSelection(opc)

        val aj_curso: Spinner = root.findViewById(R.id.spinnerAjustesCurso)

        var opc2: Int = 0

        for (it in listaCursos) {
            if (it.equals(cic.curso.toString())) {
                break
            }
            opc2++
        }

        aj_curso.setSelection(opc2)

        //-------------------------------------------------------------------------------------------------------------


        val aj_pwd: TextView = root.findViewById(R.id.editTextAjustesContrasenia)
        aj_pwd.text = alumno.contrasenia

        val aj_confPwd: TextView = root.findViewById(R.id.editTextAjustesConfirmarContrasenia)
        aj_confPwd.text = alumno.contrasenia

        return root
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    //Funcion onClick del boton Registrarse
    fun actualizar() {
        try {

            alumnosDBHelper = AlumnoDBHelper(requireContext())
            cicloDBHelper = CicloDBHelper(requireContext())

            var nombre: String
            var email: String
            var contrasenia: String
            var comproContrasenia: String
            var ciclo: String
            var curso: String
            var idciclo: Int

            nombre = editTextAjustesNombre.text.toString()
            email = editTextAjustesEmail.text.toString()
            ciclo = spinnerAjustesCiclo.selectedItem.toString()
            curso = spinnerAjustesCurso.selectedItem.toString()
            contrasenia = editTextAjustesContrasenia.text.toString()
            comproContrasenia = editTextAjustesConfirmarContrasenia.text.toString()

            if (nombre.equals("") || email.equals("") || ciclo.equals("") || contrasenia.equals("") || comproContrasenia.equals(
                    ""
                )
                || curso.equals("")
            ) {

                Toast.makeText(requireContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show()

            } else {
                var alumnos = alumnosDBHelper.selectAlumnoEmail(email)

                // Modificamos el alumno que nos devuelva
                // Si las contrasenias coinciden...
                if (contrasenia.equals(comproContrasenia)) {
                    // Buscamos el id del ciclo
                    var ciclos = cicloDBHelper.selectCicloByCursoNombre(ciclo, Integer.parseInt(curso))
                    idciclo = ciclos[0].idciclo

                    alumno = Alumno(alumno.idalumno, nombre, email, contrasenia, idciclo, 0)

                    if (alumnosDBHelper.updateAlumno(alumno)) {
                        Toast.makeText(requireContext(), "Usuario modificado correctamente", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(requireContext(), "Las contraseñas no coindicen", Toast.LENGTH_SHORT).show()
                }

            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Se ha producido un Error", Toast.LENGTH_SHORT).show()
        }
    }

}