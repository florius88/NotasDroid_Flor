package android.flor.notasdroidapp_flor.ui.mi_expediente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.flor.notasdroidapp_flor.R

class MiExpedienteFragment : Fragment() {

    private lateinit var miExpedienteViewModel: MiExpedienteViewModel

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
        return root
    }
}