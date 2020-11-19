package android.flor.notasdroidapp_flor.controladorDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.flor.notasdroidapp_flor.modelo.Ciclo
import android.flor.notasdroidapp_flor.modelo.Ciclo_Asignatura
import android.flor.notasdroidapp_flor.modeloDAO.DBCiclo

import java.util.ArrayList

class CicloDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "notas_droid_flor.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBCiclo.CicloDAO.TABLE_NAME + " (" +
                    DBCiclo.CicloDAO.COLUMN_ID_CICLO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBCiclo.CicloDAO.COLUMN_NOMBRE + " TEXT," +
                    DBCiclo.CicloDAO.COLUMN_SIGLAS + " TEXT," +
                    DBCiclo.CicloDAO.COLUMN_CURSO + " INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBCiclo.CicloDAO.TABLE_NAME
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
     * Funcion para insertar un Ciclo en la BDD
     */
    @Throws(SQLiteConstraintException::class)
    fun insertCiclo(ciclo: Ciclo): Boolean {
        // Obtiene la BD en modo escritura
        val db = writableDatabase

        // Inserta un nuevo Alumno
        val values = ContentValues()
        // values.put(DBCiclo.CicloDAO.COLUMN_ID_CICLO, ciclo.idciclo) ------ Se supone que se incrementa solo
        values.put(DBCiclo.CicloDAO.COLUMN_NOMBRE, ciclo.nombre)
        values.put(DBCiclo.CicloDAO.COLUMN_SIGLAS, ciclo.siglas)
        values.put(DBCiclo.CicloDAO.COLUMN_CURSO, ciclo.curso)

        // Inserta el alumno nuevo devolviendo la primary key
        val nuevoCicloId = db.insert(DBCiclo.CicloDAO.TABLE_NAME, null, values)

        return true
    }

    /**
     * Funcion que devuelve un Ciclo en una lista a partir de un "idciclo" pasado por parametro
     */
    fun selectCiclo(idciclo: Int): ArrayList<Ciclo> {
        val ciclos = ArrayList<Ciclo>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBCiclo.CicloDAO.TABLE_NAME + " WHERE " + DBCiclo.CicloDAO.COLUMN_ID_CICLO + "='" + idciclo + "'",
                null
            )
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var nombre: String
        var siglas: String
        var curso: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nombre = cursor.getString(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_NOMBRE))
                siglas = cursor.getString(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_SIGLAS))
                curso = cursor.getInt(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_CURSO))

                ciclos.add(Ciclo(idciclo, nombre, siglas, curso))
                cursor.moveToNext()
            }
        }
        return ciclos
    }

    /**
     * Funcion que devuelve todos los Ciclos de la BDD
     */
    fun selectAllCiclos(): ArrayList<Ciclo> {
        val ciclos = ArrayList<Ciclo>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBCiclo.CicloDAO.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idciclo: Int
        var nombre: String
        var siglas: String
        var curso: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idciclo = cursor.getInt(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_ID_CICLO))
                nombre = cursor.getString(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_NOMBRE))
                siglas = cursor.getString(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_SIGLAS))
                curso = cursor.getInt(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_CURSO))

                ciclos.add(Ciclo(idciclo, nombre, siglas, curso))
                cursor.moveToNext()
            }
        }
        return ciclos
    }

    /**
     * Funcion que devuelve un Ciclo en una lista a partir de un "curso" pasado por parametro
     * Usado para rellenar el Spinner del Registro
     */
    fun selectCicloByCurso(curso: Int): ArrayList<String> {
        val ciclos = ArrayList<String>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBCiclo.CicloDAO.TABLE_NAME + " WHERE " + DBCiclo.CicloDAO.COLUMN_CURSO + "='" + curso + "'",
                null
            )
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var nombre: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nombre = cursor.getString(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_NOMBRE))

                ciclos.add(nombre)
                cursor.moveToNext()
            }
        }
        return ciclos
    }

    /**
     * Funcion que devuelve un Ciclo en una lista a partir de un "curso" y un "nombre" pasados por parametro
     */
    fun selectCicloByCursoNombre(nombre: String, curso: Int): ArrayList<Ciclo>{

        val ciclos = ArrayList<Ciclo>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBCiclo.CicloDAO.TABLE_NAME + " WHERE " + DBCiclo.CicloDAO.COLUMN_CURSO + "='" + curso + "' AND "
                        + DBCiclo.CicloDAO.COLUMN_NOMBRE + "='" + nombre + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idciclo: Int
        var siglas: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idciclo = cursor.getInt(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_ID_CICLO))
                siglas = cursor.getString(cursor.getColumnIndex(DBCiclo.CicloDAO.COLUMN_SIGLAS))

                ciclos.add(Ciclo(idciclo, nombre, siglas, curso))
                cursor.moveToNext()
            }
        }
        return ciclos
    }

    /**
     * Funcion que inserta los valores necesarios a la tabla Ciclos
     */
    fun insertarTodosCiclos(): Unit {
        if (this.selectAllCiclos().isEmpty()) {
            insertCiclo(Ciclo(0, "Administración de Sistemas Informáticos en Red", "ASIR", 1))
            insertCiclo(Ciclo(0, "Administración de Sistemas Informáticos en Red", "ASIR", 2))
            insertCiclo(Ciclo(0, "Desarrollo de Aplicaciones Multiplataforma", "DAM", 1))
            insertCiclo(Ciclo(0, "Desarrollo de Aplicaciones Multiplataforma", "DAM", 2))
            insertCiclo(Ciclo(0, "Desarrollo de Aplicaciones Web", "DAW", 1))
            insertCiclo(Ciclo(0, "Desarrollo de Aplicaciones Web", "DAW", 2))
        }
    }
}