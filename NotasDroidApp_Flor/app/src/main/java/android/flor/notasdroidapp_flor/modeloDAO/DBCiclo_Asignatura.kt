package android.flor.notasdroidapp_flor.modelo

import android.provider.BaseColumns

object DBCiclo_Asignatura {

    class Ciclo_AsignaturaDAO : BaseColumns {
        companion object {
            val TABLE_NAME = "ciclo_asignatura"
            val COLUMN_ID_CICLO = "idciclo"
            val COLUMN_ID_ASIGNATURA = "idasignatura"
        }
    }
}