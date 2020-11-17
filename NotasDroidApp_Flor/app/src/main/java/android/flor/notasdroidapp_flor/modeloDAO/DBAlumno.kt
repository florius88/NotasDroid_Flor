package android.flor.notasdroidapp_flor.modelo

import android.provider.BaseColumns

object DBAlumno {

    class AlumnoDAO : BaseColumns {
        companion object {
            val TABLE_NAME = "alumnos"
            val COLUMN_ID_CICLO = "idalumno"
            val COLUMN_NOMBRE = "nombre"
            val COLUMN_EMAIL = "email"
            val COLUMN_CONTRASENIA = "contrasenia"
            val COLUMN_CICLO = "ciclo"
        }
    }
}