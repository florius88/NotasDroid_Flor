package android.flor.notasdroidapp_flor.modelo

import android.provider.BaseColumns

object DBAsignatura {

    class AsignaturaDAO : BaseColumns{
        companion object {
            val TABLE_NAME = "asignaturas"
            val COLUMN_ID_ASIGNATURA = "idasignatura"
            val COLUMN_NOMBRE = "nombre"
            val COLUMN_SIGLAS = "siglas"
        }
    }
}