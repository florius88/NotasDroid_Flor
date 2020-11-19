package android.flor.notasdroidapp_flor.ui.mi_matricula

import android.flor.notasdroidapp_flor.R
import android.flor.notasdroidapp_flor.modelo.Asignatura
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_asignatura.view.*

/**
 * Adaptador de la Lista de Asignaturas
 */
class AsignaturasListAdapter(
    private val listaAsignaturas: MutableList<Asignatura>,
    private val listener: (Asignatura) -> Unit
) :
    RecyclerView.Adapter<AsignaturasListAdapter.AsignaturaViewHolder>() {

    /**
     * Asociamos la vista
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignaturaViewHolder {
        return AsignaturaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_asignatura, parent, false)
        )
    }

    /**
     * Procesamos las asignaturas y las metemos en un Holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: AsignaturaViewHolder, position: Int) {
        var titular: String = listaAsignaturas[position].nombre

        //Controlamos la longitud para que si llega a una cantidad de caracteres, recortarlo
        if (titular.length >= 30) {
            titular = titular.substring(0, 30)
            holder.tvTitular.text = "$titular..."
        } else {
            holder.tvTitular.text = titular
        }

        //Formateamos la fecha
        /*val date = Date(listaNoticias[position].fecha)
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaFormato: String = formatoFecha.format(date)
        holder.tvFecha.text = fechaFormato*/
        //Sacamos la hora
        //holder.tvHora.text = listaNoticias[position].fecha.substring(16, 25)
        //Usando Picasso para poder obtener las fotos y redondearlas
        /*Picasso.get()
            .load(listaNoticias[position].imagen) //Instanciamos un objeto de la clase (creada más abajo) para redondear la imagen
            .transform(CirculoTransformacion())
            .resize(375, 200)
            .into(holder.ivNoticia)*/

        // Programamos el clic de cada fila (itemView)
        holder.tvTitular
            .setOnClickListener {
                // Devolvemos la noticia
                listener(listaAsignaturas[position])
            }
    }

    /**
     * Elimina un item de la lista
     *
     * @param position
     */
    fun removeItem(position: Int) {
        listaAsignaturas.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listaAsignaturas.size)
    }

    /**
     * Recupera un Item de la lista
     *
     * @param item
     * @param position
     */
    fun restoreItem(item: Asignatura, position: Int) {
        listaAsignaturas.add(position, item)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, listaAsignaturas.size)
    }

    /**
     * Devuelve el número de items de la lista
     *
     * @return
     */
    override fun getItemCount(): Int {
        return listaAsignaturas.size
    }

    /**
     * Holder que encapsula los objetos a mostrar en la lista
     */
    class AsignaturaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Elementos graficos con los que nos asociamos
        var ivNoticia = itemView.itemPruebaImageView
        var tvTitular = itemView.itemPrueba
        //var tvFecha = itemView.itemPruebaTextFecha
        //var tvHora = itemView.itemPruebaTextHora

        // Indicamos el Layout para el click
        // var relativeLayout = itemView.itemNoticiaLayout
    }
}
