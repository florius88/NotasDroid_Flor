package android.flor.notasdroidapp_flor.ui.mi_matricula

import android.flor.notasdroidapp_flor.PrincipalActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.flor.notasdroidapp_flor.R
import android.flor.notasdroidapp_flor.controladorDB.AsignaturaDBHelper
import android.flor.notasdroidapp_flor.controladorDB.Ciclo_AsignaturaDBHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.modelo.Asignatura
import android.flor.notasdroidapp_flor.modelo.Ciclo_Asignatura
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.flor.notasdroidapp_flor.ui.ajustes.AjustesFragment
import android.graphics.*
import android.os.AsyncTask
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_mi_matricula.*
import java.util.ArrayList

class MiMatriculaFragment : Fragment(), IOnBackPressed {

    private lateinit var asigDBHelper: AsignaturaDBHelper
    private lateinit var cicloAsigDBHelper: Ciclo_AsignaturaDBHelper
    private lateinit var miMatriculaViewModel: MiMatriculaViewModel

    private lateinit var tarea: MiMatriculaFragment.TareaCargarAsignatura // Tarea en segundo plano
    private var asignaturas = mutableListOf<Asignatura>() // Lista
    private lateinit var adapter: AsignaturasListAdapter //Adaptador de Asignaturaas de Recycler
    private var paintSweep = Paint()

    lateinit var alumno: Alumno

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        miMatriculaViewModel =
            ViewModelProviders.of(this).get(MiMatriculaViewModel::class.java)

        alumno = (activity as PrincipalActivity?)!!.alumno

        return inflater.inflate(R.layout.fragment_mi_matricula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Iniciamos la interfaz
        initUI()
    }

    private fun initUI() {

        // iniciamos el swipe para recargar
        iniciarSwipeRecarga();

        // Cargamos los datos pro primera vez
        cargarAsignaturas()

        // solo si hemos cargado hacemos sl swipeHorizontal
        iniciarSwipeHorizontal();

        // Mostramos las vistas de listas y adaptador asociado
        PruebasRecycler.layoutManager = LinearLayoutManager(context);
    }

    /**
     * Iniciamos el swipe de recarga
     */
    private fun iniciarSwipeRecarga() {
        PruebasSwipe.setColorSchemeResources(R.color.colorPrimaryDark)
        PruebasSwipe.setProgressBackgroundColorSchemeResource(R.color.primary_text)
        PruebasSwipe.setOnRefreshListener{
            cargarAsignaturas()
        }
    }

    /**
     * Carga las Asignaturas
     */
    private fun cargarAsignaturas() {
        asigDBHelper = AsignaturaDBHelper(requireContext())
        cicloAsigDBHelper = Ciclo_AsignaturaDBHelper(requireContext())
        tarea = TareaCargarAsignatura()
        tarea.execute()
    }

    /**
     * Tarea asíncrona para la carga de las asignaturas
     */
    inner class TareaCargarAsignatura : AsyncTask<String?, Void?, Void?>() {

        override fun doInBackground(vararg p0: String?): Void? {
            try {
                val lista: ArrayList<Ciclo_Asignatura> = cicloAsigDBHelper.selectCicloAsignatura(alumno.ciclo)
                val listaAsig: ArrayList<Asignatura> = asigDBHelper.selectAllAsignaturas()

                for (itAsig in listaAsig){
                    for (it in lista){
                        if (it.idasignatura == itAsig.idasignatura){
                            asignaturas.add(itAsig)
                        }
                    }
                }
            } catch (e: Exception) {
            }
            return null
        }

        /**
         * Procedimiento a realizar al terminar
         * Cargamos la lista
         *
         * @param args
         */
        override fun onPostExecute(args: Void?) {
            adapter = AsignaturasListAdapter(asignaturas) {
                eventoClicFila(it)
            }

            PruebasRecycler.adapter = adapter
            // Avismos que ha cambiado
            adapter.notifyDataSetChanged()
            PruebasRecycler.setHasFixedSize(true)
            PruebasSwipe.isRefreshing = false
        }

        /**
         * Evento cli asociado a una fila
         * @param asig Asignatura
         */
        private fun eventoClicFila(asig: Asignatura) {
            if((activity as PrincipalActivity?)!!.isClicEventoFila) {
                abrirAsignatura(asig)
            }
        }
    }

    /**
     * Realiza el swipe horizontal si es necesario
     */
    private fun iniciarSwipeHorizontal() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT
        ) {
            // Sobreescribimos los métodos
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Analizamos el evento según la dirección
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                // Si pulsamos a la de izquierda o a la derecha
                // Programamos la accion
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        editarElemento(position)
                    }
                }
            }

            // Dibujamos los botones
            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    // Si es dirección a la derecha: izquierda->derecta
                    // Pintamos de azul y ponemos el icono
                    if (dX > 0) {
                        // Pintamos el botón izquierdo
                        botonIzquierdo(canvas, dX, itemView, width)
                    }
                }
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        // Añadimos los eventos al RV
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(PruebasRecycler)
    }

    /**
     * Acción secundaria: Ver/Editar
     * @param position Int
     */
    private fun editarElemento(position: Int){
        val asignatura = asignaturas[position]
        abrirAsignatura(asignatura)
        // Esto es para que no se quede el color. Quitamos y ponemos la noticia
        adapter.removeItem(position)
        adapter.restoreItem(asignatura, position);
    }

    /**
     * Abre una noticia como Fragment
     * @param asig Asignatura
     */
    private fun abrirAsignatura(asig: Asignatura) {
        val pruebas = PruebasFragment(asig)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        //Llamamos al replace
        transaction.replace(R.id.fragment_mi_matricula, pruebas)
        //nav_host_fragment
        //fragment_mi_matricula

        //nav_view--> NO
        //nav_miMatricula-->NO
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Mostramos el elemento izquierdo
     * @param canvas Canvas
     * @param dX Float
     * @param itemView View
     * @param width Float
     */
    private fun botonIzquierdo(canvas: Canvas, dX: Float, itemView: View, width: Float) {
        // Pintamos de gris y ponemos el icono
        paintSweep.setColor(Color.DKGRAY)
        val background = RectF(
            itemView.left.toFloat(), itemView.top.toFloat(), dX,
            itemView.bottom.toFloat()
        )
        canvas.drawRect(background, paintSweep)
        val icon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_sweep_detalles)
        val iconDest = RectF(
            itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left
                .toFloat() + 2 * width, itemView.bottom.toFloat() - width
        )
        canvas.drawBitmap(icon, null, iconDest, paintSweep)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}