package android.flor.notasdroidapp_flor.ui.mi_matricula

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

class MiMatriculaFragment : Fragment(), IOnBackPressed {

    private lateinit var miMatriculaViewModel: MiMatriculaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        miMatriculaViewModel =
            ViewModelProviders.of(this).get(MiMatriculaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mi_matricula, container, false)
//        val textView: TextView = root.findViewById(R.id.nav_miMatricula)
//        miMatriculaViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}