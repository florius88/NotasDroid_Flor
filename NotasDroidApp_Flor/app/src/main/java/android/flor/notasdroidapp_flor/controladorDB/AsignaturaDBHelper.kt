package android.flor.notasdroidapp_flor.controladorDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.flor.notasdroidapp_flor.modelo.Asignatura
import android.flor.notasdroidapp_flor.modeloDAO.DBAsignatura

import java.util.ArrayList

class AsignaturaDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "notas_droid_flor.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBAsignatura.AsignaturaDAO.TABLE_NAME + " (" +
                    DBAsignatura.AsignaturaDAO.COLUMN_ID_ASIGNATURA + " INTEGER PRIMARY KEY," +
                    DBAsignatura.AsignaturaDAO.COLUMN_NOMBRE + " TEXT," +
                    DBAsignatura.AsignaturaDAO.COLUMN_SIGLAS + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBAsignatura.AsignaturaDAO.TABLE_NAME

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    /**
     * Funcion para insertar una Asignatura en la BDD
     */
    @Throws(SQLiteConstraintException::class)
    fun insertAsignatura(asignatura: Asignatura): Boolean {
        // Obtiene la BD en modo escritura
        val db = writableDatabase

        // Inserta un nuevo Alumno
        val values = ContentValues()
        values.put(DBAsignatura.AsignaturaDAO.COLUMN_ID_ASIGNATURA, asignatura.idasignatura)
        values.put(DBAsignatura.AsignaturaDAO.COLUMN_NOMBRE, asignatura.nombre)
        values.put(DBAsignatura.AsignaturaDAO.COLUMN_SIGLAS, asignatura.siglas)

        // Inserta el alumno nuevo devolviendo la primary key
        val nuevaAsignaturaId = db.insert(DBAsignatura.AsignaturaDAO.TABLE_NAME, null, values)

        return true
    }

    /**
     * Funcion que devuelve una Asignatura en una lista a partir de un idasignatura pasado por parametro
     */
    fun selectAsignatura(idasignatura: Int): ArrayList<Asignatura> {
        val asignaturas = ArrayList<Asignatura>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBAsignatura.AsignaturaDAO.TABLE_NAME + " WHERE " + DBAsignatura.AsignaturaDAO.COLUMN_ID_ASIGNATURA + "='" + idasignatura + "'",
                null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var nombre: String
        var siglas: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nombre = cursor.getString(cursor.getColumnIndex(DBAsignatura.AsignaturaDAO.COLUMN_NOMBRE))
                siglas = cursor.getString(cursor.getColumnIndex(DBAsignatura.AsignaturaDAO.COLUMN_SIGLAS))

                asignaturas.add(Asignatura(idasignatura, nombre, siglas))
                cursor.moveToNext()
            }
        }
        return asignaturas
    }

    /**
     * Funcion que devuelve todas las Asignaturas de la BDD
     */
    fun selectAllAsignaturas(): ArrayList<Asignatura> {
        val asignaturas = ArrayList<Asignatura>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBAsignatura.AsignaturaDAO.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idasignatura: Int
        var nombre: String
        var siglas: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idasignatura = cursor.getInt(cursor.getColumnIndex(DBAsignatura.AsignaturaDAO.COLUMN_ID_ASIGNATURA))
                nombre = cursor.getString(cursor.getColumnIndex(DBAsignatura.AsignaturaDAO.COLUMN_NOMBRE))
                siglas = cursor.getString(cursor.getColumnIndex(DBAsignatura.AsignaturaDAO.COLUMN_SIGLAS))

                asignaturas.add(Asignatura(idasignatura, nombre, siglas))
                cursor.moveToNext()
            }
        }
        return asignaturas
    }

    /**
     * Funcion que inserta los valores necesarios a la tabla Asignaturas
     */
    fun insertarTodasAsignaturas(): Unit {
        if (this.selectAllAsignaturas().isEmpty()){
        insertAsignatura(Asignatura(22,"Inglés técnico para ciclos de grado superior de la familia de informética y comunicaciones", "ING"))
        insertAsignatura(Asignatura(369,"Implantación de Sistemas Operativos", "ISO"))
        insertAsignatura(Asignatura(370,"Planificación y Administración de Redes", "PAR"))
        insertAsignatura(Asignatura(371,"Fundamentos de Hardware", "FH"))
        insertAsignatura(Asignatura(372,"Gestión de Bases de Datos", "GB"))
        insertAsignatura(Asignatura(373,"Lenguajes de Marcas y Sistemas de Gestión de Información", "LMSGI"))
        insertAsignatura(Asignatura(374,"Administración de Sistemas Operativos", "ASO"))
        insertAsignatura(Asignatura(375,"Servicios de Red e Internet", "SRI"))
        insertAsignatura(Asignatura(376,"Implantación de Aplicaciones Web", "IAQ"))
        insertAsignatura(Asignatura(377,"Administración de Sistemas Gestores de Bases de Datos", "ASGBD"))
        insertAsignatura(Asignatura(378,"Seguridad y Alta Disponibilidad", "SAD"))
        insertAsignatura(Asignatura(380,"Formación y Orientación Laboral", "FOL"))
        insertAsignatura(Asignatura(381,"Empresa e Iniciativa Emprendedora", "EIE"))
        insertAsignatura(Asignatura(483,"Sistemas Informáticos", "SISIN"))
        insertAsignatura(Asignatura(484,"Bases de Datos", "BDD"))
        insertAsignatura(Asignatura(485,"Programación", "PROG"))
        insertAsignatura(Asignatura(487,"Entornos de Desarrollo", "ENDES"))
        insertAsignatura(Asignatura(493,"Formación y Orientación Laboral", "FOL"))
        insertAsignatura(Asignatura(486,"Acceso a Datos", "AD"))
        insertAsignatura(Asignatura(488,"Desarrollo de Interfaces", "DI"))
        insertAsignatura(Asignatura(489,"Programación Multimedia y Dispositivos Móviles", "PMDM"))
        insertAsignatura(Asignatura(490,"Programación de Servicios y Procesos", "PSP"))
        insertAsignatura(Asignatura(491,"Sistemas de Gestión Empresarial", "SGE"))
        insertAsignatura(Asignatura(494,"Empresa e Iniciativa Emprendedora", "EIE"))
        insertAsignatura(Asignatura(617,"Formación y Orientación Laboral", "FOL"))
        insertAsignatura(Asignatura(612,"Desarrollo Web en Entorno Cliente", "DWEC"))
        insertAsignatura(Asignatura(613,"Desarrollo Web en Entorno Servidor", "DEWS"))
        insertAsignatura(Asignatura(614,"Despliegue de Aplicaciones Web", "DAW"))
        insertAsignatura(Asignatura(615,"Diseño de Interfaces Web", "DIW"))
        insertAsignatura(Asignatura(618,"Empresa e iniciativa Emprenderora", "EIE"))
        }
    }
}