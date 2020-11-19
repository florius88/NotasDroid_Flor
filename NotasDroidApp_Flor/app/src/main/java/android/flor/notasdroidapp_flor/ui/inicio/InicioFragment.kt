package android.flor.notasdroidapp_flor.ui.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.flor.notasdroidapp_flor.R
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class InicioFragment : Fragment(), IOnBackPressed {

    private var backPressedTime:Long = 0
    lateinit var backToast:Toast

    private lateinit var inicioViewModel: InicioViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inicioViewModel =
            ViewModelProviders.of(this).get(InicioViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_inicio, container, false)
        val textView: TextView = root.findViewById(R.id.nav_inicio)
        inicioViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = "Bienvenido a su aplicación de notas"
        })
        return root
    }

    override fun onBackPressed(): Boolean {
        backToast = Toast.makeText(activity, "Presione de nuevo para salir de la aplicación.", Toast.LENGTH_LONG)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            activity?.finishAffinity()
            exitProcess(0)
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
        return false
    }
}