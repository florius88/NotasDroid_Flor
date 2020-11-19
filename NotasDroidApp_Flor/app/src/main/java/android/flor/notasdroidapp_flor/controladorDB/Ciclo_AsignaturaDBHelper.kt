package android.flor.notasdroidapp_flor.controladorDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.flor.notasdroidapp_flor.modelo.Asignatura
import android.flor.notasdroidapp_flor.modelo.Ciclo_Asignatura
import android.flor.notasdroidapp_flor.modeloDAO.DBCiclo_Asignatura

import java.util.ArrayList
import kotlin.jvm.Throws

class Ciclo_AsignaturaDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "notas_droid_flor.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBCiclo_Asignatura.Ciclo_AsignaturaDAO.TABLE_NAME + " (" +
                    DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_ASIGNATURA + " INTEGER," +
                    DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_CICLO + " INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBCiclo_Asignatura.Ciclo_AsignaturaDAO.TABLE_NAME
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
     * Funcion para insertar una relacion ciclo-asignatura en la BDD
     */
    @Throws(SQLiteConstraintException::class)
    fun insertCicloAsignatura(ciclo_asig: Ciclo_Asignatura): Boolean {
        // Obtiene la BD en modo escritura
        val db = writableDatabase

        // Inserta un nuevo Alumno
        val values = ContentValues()
        values.put(DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_ASIGNATURA, ciclo_asig.idasignatura)
        values.put(DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_CICLO, ciclo_asig.idciclo)

        // Inserta la nueva relacion ciclo-asignatura
        db.insert(DBCiclo_Asignatura.Ciclo_AsignaturaDAO.TABLE_NAME, null, values)

        return true
    }

    /**
     * Funcion que devuelve una relacion ciclo-asignatura en una lista a partir de un "idciclo" pasado por parametro
     */
    fun selectCicloAsignatura(idciclo: Int): ArrayList<Ciclo_Asignatura> {
        val relac = ArrayList<Ciclo_Asignatura>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBCiclo_Asignatura.Ciclo_AsignaturaDAO.TABLE_NAME + " WHERE " + DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_CICLO + "='" + idciclo + "'",
                null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idasignatura: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idasignatura = cursor.getInt(cursor.getColumnIndex(DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_ASIGNATURA))

                relac.add(Ciclo_Asignatura(idasignatura, idciclo))
                cursor.moveToNext()
            }
        }
        return relac
    }

    /**
     * Funcion que devuelve todas las relaciones ciclo-asignatura de la BDD
     */
    fun selectAllCicloAsignaturas(): ArrayList<Ciclo_Asignatura> {
        val relac = ArrayList<Ciclo_Asignatura>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBCiclo_Asignatura.Ciclo_AsignaturaDAO.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idciclo: Int
        var idasignatura: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idciclo = cursor.getInt(cursor.getColumnIndex(DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_CICLO))
                idasignatura = cursor.getInt(cursor.getColumnIndex(DBCiclo_Asignatura.Ciclo_AsignaturaDAO.COLUMN_ID_ASIGNATURA))

                relac.add(Ciclo_Asignatura(idasignatura, idciclo))
                cursor.moveToNext()
            }
        }
        return relac
    }

    /**
     * Funcion que inserta los valores necesarios a la tabla Ciclo-Asignatura
     */
    fun insertarTodasRelaciones(): Unit {
        if (this.selectAllCicloAsignaturas().isEmpty()) {
            insertCicloAsignatura(Ciclo_Asignatura(22,1))
            insertCicloAsignatura(Ciclo_Asignatura(22,3))
            insertCicloAsignatura(Ciclo_Asignatura(22,5))
            insertCicloAsignatura(Ciclo_Asignatura(369,1))
            insertCicloAsignatura(Ciclo_Asignatura(370,1))
            insertCicloAsignatura(Ciclo_Asignatura(371,1))
            insertCicloAsignatura(Ciclo_Asignatura(372,1))
            insertCicloAsignatura(Ciclo_Asignatura(373,1))
            insertCicloAsignatura(Ciclo_Asignatura(373,3))
            insertCicloAsignatura(Ciclo_Asignatura(373,5))
            insertCicloAsignatura(Ciclo_Asignatura(374,2))
            insertCicloAsignatura(Ciclo_Asignatura(375,2))
            insertCicloAsignatura(Ciclo_Asignatura(376,2))
            insertCicloAsignatura(Ciclo_Asignatura(377,2))
            insertCicloAsignatura(Ciclo_Asignatura(378,2))
            insertCicloAsignatura(Ciclo_Asignatura(380,1))
            insertCicloAsignatura(Ciclo_Asignatura(381,2))
            insertCicloAsignatura(Ciclo_Asignatura(483,3))
            insertCicloAsignatura(Ciclo_Asignatura(483,5))
            insertCicloAsignatura(Ciclo_Asignatura(484,3))
            insertCicloAsignatura(Ciclo_Asignatura(484,5))
            insertCicloAsignatura(Ciclo_Asignatura(485,3))
            insertCicloAsignatura(Ciclo_Asignatura(485,5))
            insertCicloAsignatura(Ciclo_Asignatura(487,3))
            insertCicloAsignatura(Ciclo_Asignatura(487,5))
            insertCicloAsignatura(Ciclo_Asignatura(493,3))
            insertCicloAsignatura(Ciclo_Asignatura(486,4))
            insertCicloAsignatura(Ciclo_Asignatura(488,4))
            insertCicloAsignatura(Ciclo_Asignatura(489,4))
            insertCicloAsignatura(Ciclo_Asignatura(490,4))
            insertCicloAsignatura(Ciclo_Asignatura(491,4))
            insertCicloAsignatura(Ciclo_Asignatura(494,4))
            insertCicloAsignatura(Ciclo_Asignatura(617,5))
            insertCicloAsignatura(Ciclo_Asignatura(612,6))
            insertCicloAsignatura(Ciclo_Asignatura(613,6))
            insertCicloAsignatura(Ciclo_Asignatura(614,6))
            insertCicloAsignatura(Ciclo_Asignatura(615,6))
            insertCicloAsignatura(Ciclo_Asignatura(618,6))
        }
    }

}