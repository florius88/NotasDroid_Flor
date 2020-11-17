package android.flor.notasdroidapp_flor.modelo

import android.provider.BaseColumns

object DBPrueba {

    class PruebaDAO : BaseColumns {
        companion object {
            val TABLE_NAME = "pruebas"
            val COLUMN_ID_CICLO = "idprueba"
            val COLUMN_ID_CICLO = "idalumno"
            val COLUMN_ID_ASIGNATURA = "idasignatura"
            val COLUMN_TITULO = "titulo"
            val COLUMN_DESCRIPCION = "descripcion"
            val COLUMN_FECHA = "fecha"
            val COLUMN_REALIZADA = "realizada"
            val COLUMN_CALIFICACION = "calificacion"
        }
    }
}