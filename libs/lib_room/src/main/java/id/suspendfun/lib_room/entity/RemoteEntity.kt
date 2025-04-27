package id.suspendfun.lib_room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote")
data class RemoteEntity(
    @PrimaryKey val id: String = "pokemon",
    val lastUpdated: Long
)
