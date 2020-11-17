package android.flor.notasdroidapp_flor.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "asignaturas")
class Asignatura (var nombre: String, var siglas: String, @PrimaryKey(autoGenerate = true) var idasignatura: Int = 0): Serializable {

}