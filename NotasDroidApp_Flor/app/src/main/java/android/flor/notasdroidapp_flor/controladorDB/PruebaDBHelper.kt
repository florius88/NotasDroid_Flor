package android.flor.notasdroidapp_flor.controladorDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.flor.notasdroidapp_flor.modelo.Ciclo_Asignatura
import android.flor.notasdroidapp_flor.modelo.Prueba
import android.flor.notasdroidapp_flor.modeloDAO.DBPrueba

import java.util.ArrayList

class PruebaDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "notas_droid_flor.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBPrueba.PruebaDAO.TABLE_NAME + " (" +
                    DBPrueba.PruebaDAO.COLUMN_ID_PRUEBA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBPrueba.PruebaDAO.COLUMN_ID_ALUMNO + " INTEGER," +
                    DBPrueba.PruebaDAO.COLUMN_ID_ASIGNATURA + " INTEGER," +
                    DBPrueba.PruebaDAO.COLUMN_TITULO + " TEXT," +
                    DBPrueba.PruebaDAO.COLUMN_DESCRIPCION + " TEXT," +
                    DBPrueba.PruebaDAO.COLUMN_FECHA + " TEXT," +
                    DBPrueba.PruebaDAO.COLUMN_REALIZADA + " BOOLEAN," +
                    DBPrueba.PruebaDAO.COLUMN_CALIFICACION + " REAL)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBPrueba.PruebaDAO.TABLE_NAME
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
     * Funcion para insertar ua Prueba en la BDD
     */
    @Throws(SQLiteConstraintException::class)
    fun insertPrueba(prueba: Prueba): Boolean {
        // Obtiene la BD en modo escritura
        val db = writableDatabase

        // Inserta un nuevo Alumno
        val values = ContentValues()
        // values.put(DBPrueba.PruebaDAO.COLUMN_ID_PRUEBA, prueba.idprueba) ------ Se supone que se incrementa solo
        values.put(DBPrueba.PruebaDAO.COLUMN_ID_ALUMNO, prueba.idalumno)
        values.put(DBPrueba.PruebaDAO.COLUMN_ID_ASIGNATURA, prueba.idasignatura)
        values.put(DBPrueba.PruebaDAO.COLUMN_TITULO, prueba.titulo)
        values.put(DBPrueba.PruebaDAO.COLUMN_DESCRIPCION, prueba.descripcion)
        values.put(DBPrueba.PruebaDAO.COLUMN_FECHA, prueba.fecha)
        values.put(DBPrueba.PruebaDAO.COLUMN_REALIZADA, prueba.realizada)
        values.put(DBPrueba.PruebaDAO.COLUMN_CALIFICACION, prueba.calificacion)

        // Inserta el alumno nuevo devolviendo la primary key
        val nuevaPruebaId = db.insert(DBPrueba.PruebaDAO.TABLE_NAME, null, values)

        return true
    }

    /**
     * Funcion que devuelve una Prueba en una lista a partir de un "idprueba" pasado por parametro
     */
    fun selectPrueba(idprueba: Int): ArrayList<Prueba> {
        val pruebas = ArrayList<Prueba>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBPrueba.PruebaDAO.TABLE_NAME + " WHERE " + DBPrueba.PruebaDAO.COLUMN_ID_PRUEBA + "='" + idprueba + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idalumno: Int
        var idasignatura: Int
        var titulo: String
        var descripcion: String
        var fecha: String
        var realizada: Int
        var calificacion: Double

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idalumno = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_ID_ALUMNO))
                idasignatura = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_ID_ASIGNATURA))
                titulo = cursor.getString(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_TITULO))
                descripcion = cursor.getString(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_DESCRIPCION))
                fecha = cursor.getString(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_FECHA))
                realizada = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_REALIZADA))
                calificacion = cursor.getDouble(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_CALIFICACION))

                pruebas.add(Prueba(idprueba, idalumno, idasignatura, titulo, descripcion, fecha, realizada, calificacion))
                cursor.moveToNext()
            }
        }
        return pruebas
    }

    /**
     * Funcion que devuelve todas las Pruebas de la BDD
     */
    fun selectAllPruebas(): ArrayList<Prueba> {
        val pruebas = ArrayList<Prueba>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBPrueba.PruebaDAO.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idprueba: Int
        var idalumno: Int
        var idasignatura: Int
        var titulo: String
        var descripcion: String
        var fecha: String
        var realizada: Int
        var calificacion: Double

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idprueba = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_ID_PRUEBA))
                idalumno = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_ID_ALUMNO))
                idasignatura = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_ID_ASIGNATURA))
                titulo = cursor.getString(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_TITULO))
                descripcion = cursor.getString(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_DESCRIPCION))
                fecha = cursor.getString(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_FECHA))
                realizada = cursor.getInt(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_REALIZADA))
                calificacion = cursor.getDouble(cursor.getColumnIndex(DBPrueba.PruebaDAO.COLUMN_CALIFICACION))

                pruebas.add(Prueba(idprueba, idalumno, idasignatura, titulo, descripcion, fecha, realizada, calificacion))
                cursor.moveToNext()
            }
        }
        return pruebas
    }

}