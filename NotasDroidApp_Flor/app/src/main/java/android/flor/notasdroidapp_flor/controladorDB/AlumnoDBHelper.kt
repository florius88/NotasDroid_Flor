package android.flor.notasdroidapp_flor.controladorDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.flor.notasdroidapp_flor.modelo.Alumno
import android.flor.notasdroidapp_flor.modeloDAO.DBAlumno

import java.util.ArrayList

class AlumnoDBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "notas_droid_flor.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBAlumno.AlumnoDAO.TABLE_NAME + " (" +
                    DBAlumno.AlumnoDAO.COLUMN_ID_ALUMNO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBAlumno.AlumnoDAO.COLUMN_NOMBRE + " TEXT," +
                    DBAlumno.AlumnoDAO.COLUMN_EMAIL + " TEXT," +
                    DBAlumno.AlumnoDAO.COLUMN_CONTRASENIA + " TEXT," +
                    DBAlumno.AlumnoDAO.COLUMN_CICLO + " INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBAlumno.AlumnoDAO.TABLE_NAME
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
     * Funcion para insertar un Alumno en la BDD
     */
    @Throws(SQLiteConstraintException::class)
    fun insertAlumno(alumno: Alumno): Boolean {
        // Obtiene la BD en modo escritura
        val db = writableDatabase

        // Inserta un nuevo Alumno
        val values = ContentValues()
        // values.put(DBAlumno.AlumnoDAO.COLUMN_ID_CICLO, alumno.idalumno) ------ Se supone que se incrementa solo
        values.put(DBAlumno.AlumnoDAO.COLUMN_NOMBRE, alumno.nombre)
        values.put(DBAlumno.AlumnoDAO.COLUMN_EMAIL, alumno.email)
        values.put(DBAlumno.AlumnoDAO.COLUMN_CONTRASENIA, alumno.contrasenia)
        values.put(DBAlumno.AlumnoDAO.COLUMN_CICLO, alumno.ciclo)

        // Inserta el alumno nuevo devolviendo la primary key
        val nuevoAlumnoId = db.insert(DBAlumno.AlumnoDAO.TABLE_NAME, null, values)

        return true
    }

    /**
     * Funcion que devuelve un Alumno en una lista a partir de un idalumno pasado por parametro
     */
    fun selectAlumno(idalumno: Int): ArrayList<Alumno> {
        val alumnos = ArrayList<Alumno>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBAlumno.AlumnoDAO.TABLE_NAME + " WHERE " + DBAlumno.AlumnoDAO.COLUMN_ID_ALUMNO + "='" + idalumno + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var nombre: String
        var email: String
        var contrasenia: String
        var ciclo: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nombre = cursor.getString(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_NOMBRE))
                email = cursor.getString(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_EMAIL))
                contrasenia = cursor.getString(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_CONTRASENIA))
                ciclo = cursor.getInt(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_CICLO))

                alumnos.add(Alumno(idalumno, nombre, email, contrasenia, ciclo))
                cursor.moveToNext()
            }
        }
        return alumnos
    }

    /**
     * Funcion que devuelve todos los Alumnos de la BDD
     */
    fun selectAllAlumnos(): ArrayList<Alumno> {
        val alumnos = ArrayList<Alumno>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBAlumno.AlumnoDAO.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idalumno: Int
        var nombre: String
        var email: String
        var contrasenia: String
        var ciclo: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idalumno = cursor.getInt(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_ID_ALUMNO))
                nombre = cursor.getString(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_NOMBRE))
                email = cursor.getString(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_EMAIL))
                contrasenia = cursor.getString(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_CONTRASENIA))
                ciclo = cursor.getInt(cursor.getColumnIndex(DBAlumno.AlumnoDAO.COLUMN_CICLO))

                alumnos.add(Alumno(idalumno, nombre, email, contrasenia, ciclo))
                cursor.moveToNext()
            }
        }
        return alumnos
    }

}