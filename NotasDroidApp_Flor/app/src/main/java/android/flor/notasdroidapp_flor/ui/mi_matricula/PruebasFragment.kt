package android.flor.notasdroidapp_flor.ui.mi_matricula

import android.flor.notasdroidapp_flor.PrincipalActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.flor.notasdroidapp_flor.R
import android.flor.notasdroidapp_flor.controladorDB.PruebaDBHelper
import android.flor.notasdroidapp_flor.modelo.Asignatura
import android.flor.notasdroidapp_flor.modelo.Prueba
import android.flor.notasdroidapp_flor.ui.IOnBackPressed
import android.graphics.*
import android.os.AsyncTask
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_pruebas.*

class PruebasFragment(private val asig: Asignatura) : Fragment(), IOnBackPressed {

    private lateinit var pruebaDBHelper: PruebaDBHelper

    // Mis variables
    private var pruebas = mutableListOf<Prueba>() // Lista

    // Interfaz gráfica
    private lateinit var adapter: PruebasListAdapter //Adaptador de Noticias de Recycler
    private lateinit var tarea: TareaCargarPruebas // Tarea en segundo plano
    private var paintSweep = Paint()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_pruebas, container, false)

        val fab: FloatingActionButton = root.findViewById(R.id.fabCrear)
        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            val prueba:Prueba = Prueba(0,0,0,"titulo","descripcion","fecha",0,0.0)
            val pruebaDetalle = PruebaDetalleFragment(prueba)
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            //Llamamos al replace
            transaction.replace(R.id.fragment_pruebas, pruebaDetalle)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        /*val buttonCalculate = root.findViewById(R.id.fabCrear) as Button

        buttonCalculate.setOnClickListener() {
            Toast.makeText(requireContext(), "CREAR", Toast.LENGTH_SHORT).show()
        }*/

        return root//inflater.inflate(R.layout.fragment_pruebas, container, false)
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
        cargarPruebas()

        // solo si hemos cargado hacemos sl swipeHorizontal
        iniciarSwipeHorizontal();

        // Mostramos las vistas de listas y adaptador asociado
        PruebasRecycler.layoutManager = LinearLayoutManager(context);
        Log.d("Noticias", "Asignado al RV");
    }


    /**
     * Iniciamos el swipe de recarga
     */
    private fun iniciarSwipeRecarga() {
        PruebasSwipe.setColorSchemeResources(R.color.colorPrimaryDark)
        PruebasSwipe.setProgressBackgroundColorSchemeResource(R.color.primary_text)
        PruebasSwipe.setOnRefreshListener {
            cargarPruebas()
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
                    ItemTouchHelper.LEFT -> {
                        // Log.d("Noticias", "Tocado izquierda");
                        borrarElemento(position)
                    }
                    else -> {
                        //  Log.d("Noticias", "Tocado derecha");
                        editarElemento(position)
                    }
                }
            }

            // Dibujamos los botones y eveneto. Nos lo creemos :):)
            // IMPORTANTE
            // Para que no te explote las imagenes deben ser PNG
            // Así que añade un IMAGE ASEET bjándtelos de internet
            // https://material.io/resources/icons/?style=baseline
            // como PNG y cargas el de mayor calidad
            // de otra forma Bitmap no funciona bien
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
                    } else {
                        // Caso contrario
                        botonDerecho(canvas, dX, itemView, width)
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
     * Mostramos el elemento inquierdo
     * @param canvas Canvas
     * @param dX Float
     * @param itemView View
     * @param width Float
     */
    private fun botonDerecho(canvas: Canvas, dX: Float, itemView: View, width: Float) {
        // Pintamos de rojo y ponemos el icono
        paintSweep.color = Color.RED
        val background = RectF(
            itemView.right.toFloat() + dX,
            itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat()
        )
        canvas.drawRect(background, paintSweep)
        val icon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_sweep_eliminar)
        val iconDest = RectF(
            itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right
                .toFloat() - width, itemView.bottom.toFloat() - width
        )
        canvas.drawBitmap(icon, null, iconDest, paintSweep)
    }

    /**
     * Mostramos el elemento izquierdo
     * @param canvas Canvas
     * @param dX Float
     * @param itemView View
     * @param width Float
     */
    private fun botonIzquierdo(canvas: Canvas, dX: Float, itemView: View, width: Float) {
        // Pintamos de azul y ponemos el icono
        paintSweep.setColor(Color.BLUE)
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

    /**
     * Carga las noticias
     */
    private fun cargarPruebas() {
        pruebaDBHelper = PruebaDBHelper(requireContext())
        tarea = TareaCargarPruebas()
        tarea.execute()
    }

    /**
     * Acción primaria: Borra un elemento
     * @param position Int
     */
    private fun borrarElemento(position: Int) {
        // Acciones
        val deletedModel = pruebas[position]
        adapter.removeItem(position)
        // Mostramos la barra. Se la da opción al usuario de recuperar lo borrado con el el snackbar
        val snackbar = Snackbar.make(view!!, "Noticia eliminada", Snackbar.LENGTH_LONG)
        snackbar.setAction("DESHACER") { // undo is selected, restore the deleted item
            adapter.restoreItem(deletedModel, position)
        }
        snackbar.setActionTextColor(resources.getColor(R.color.colorPrimary))
        snackbar.show()
    }

    /**
     * Acción secundaria: Ver/Editar
     * @param position Int
     */
    private fun editarElemento(position: Int) {
        val prueba = pruebas[position]
        abrirPrueba(prueba)
        // Esto es para que no se quede el color. Quitamos y ponemos la noticia
        adapter.removeItem(position)
        adapter.restoreItem(prueba, position);
    }

    /**
     * Evento cli asociado a una fila
     * @param noticia Noticia
     */
    private fun eventoClicFila(prueba: Prueba) {
        if ((activity as PrincipalActivity?)!!.isClicEventoFilaPrueba) {
            abrirPrueba(prueba)
        }
    }

    /**
     * Abre una noticia como Fragment
     * @param prueba Prueba
     */
    private fun abrirPrueba(prueba: Prueba) {
        val pruebaDetalle = PruebaDetalleFragment(prueba)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        //Llamamos al replace
        transaction.replace(R.id.fragment_pruebas, pruebaDetalle)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Tarea asíncrona para la carga de las pruebas
     */
    inner class TareaCargarPruebas : AsyncTask<String?, Void?, Void?>() {

        override fun doInBackground(vararg p0: String?): Void? {
            try {
                //asig.idasignatura
                pruebas = pruebaDBHelper.selectAllPruebas()
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
            adapter = PruebasListAdapter(pruebas) {
                eventoClicFila(it)
            }

            PruebasRecycler.adapter = adapter
            // Avismos que ha cambiado
            adapter.notifyDataSetChanged()
            PruebasRecycler.setHasFixedSize(true)
            PruebasSwipe.isRefreshing = false
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}