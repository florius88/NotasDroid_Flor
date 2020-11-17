package android.flor.notasdroidapp_flor.modeloDAO

import android.provider.BaseColumns

object DBPrueba {

    class PruebaDAO : BaseColumns {
        companion object {
            val TABLE_NAME = "pruebas"
            val COLUMN_ID_PRUEBA = "idprueba"
            val COLUMN_ID_ALUMNO = "idalumno"
            val COLUMN_ID_ASIGNATURA = "idasignatura"
            val COLUMN_TITULO = "titulo"
            val COLUMN_DESCRIPCION = "descripcion"
            val COLUMN_FECHA = "fecha"
            val COLUMN_REALIZADA = "realizada"
            val COLUMN_CALIFICACION = "calificacion"
        }
    }
}