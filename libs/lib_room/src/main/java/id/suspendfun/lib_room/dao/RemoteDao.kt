package id.suspendfun.lib_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.suspendfun.lib_room.entity.RemoteEntity

@Dao
interface RemoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteEntity: RemoteEntity)

    @Query("SELECT * FROM remote WHERE id = :id")
    suspend fun getRemoteEntity(id: String): RemoteEntity?

    @Query("DELETE FROM remote")
    suspend fun clearRemoteEntity()
}
