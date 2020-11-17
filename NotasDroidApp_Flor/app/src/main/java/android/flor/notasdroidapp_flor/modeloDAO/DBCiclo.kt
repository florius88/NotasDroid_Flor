package android.flor.notasdroidapp_flor.modelo

import android.provider.BaseColumns

object DBCiclo {

    class CicloDAO : BaseColumns {
        companion object {
            val TABLE_NAME = "ciclos"
            val COLUMN_ID_CICLO = "idciclo"
            val COLUMN_NOMBRE = "nombre"
            val COLUMN_SIGLAS = "siglas"
            val COLUMN_CURSO = "curso"
        }
    }
}