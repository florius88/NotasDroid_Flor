package android.flor.notasdroidapp_flor.modelo

import android.content.Context
import android.flor.notasdroidapp_flor.modeloDAO.AsignaturaDAO
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asignatura::class], version = 1)
abstract class AppBaseDatos : RoomDatabase() {

    abstract fun asignatura(): AsignaturaDAO

    companion object {
        @Volatile
        private var INSTANCE: AppBaseDatos? = null

        fun getBasedatos(context: Context): AppBaseDatos{
            val tempInstancia = INSTANCE

            if (tempInstancia != null) {
                return tempInstancia
            }

            synchronized(this){
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppBaseDatos::class.java,
                    "app_database"
                ).build()

                INSTANCE = instancia

                return instancia
            }
        }


    }


}