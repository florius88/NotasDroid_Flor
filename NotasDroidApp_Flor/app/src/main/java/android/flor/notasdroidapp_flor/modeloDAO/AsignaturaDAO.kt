package android.flor.notasdroidapp_flor.modeloDAO

import android.flor.notasdroidapp_flor.modelo.Asignatura
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AsignaturaDAO {
    @Query("SELECT * FROM asignaturas")
    fun obtenerTodas(): LiveData<List<Asignatura>>


}