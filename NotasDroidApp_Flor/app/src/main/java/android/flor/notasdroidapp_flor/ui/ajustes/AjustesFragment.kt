package android.flor.notasdroidapp_flor.ui.ajustes

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

class AjustesFragment : Fragment(), IOnBackPressed {

    private lateinit var ajustesViewModel: AjustesViewModel

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
        return root
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}