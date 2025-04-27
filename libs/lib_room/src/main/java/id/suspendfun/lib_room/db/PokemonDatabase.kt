package id.suspendfun.lib_room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.suspendfun.lib_room.dao.PokemonDao
import id.suspendfun.lib_room.dao.RemoteDao
import id.suspendfun.lib_room.entity.PokemonEntity
import id.suspendfun.lib_room.entity.RemoteEntity

@Database(entities = [PokemonEntity::class, RemoteEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteDao(): RemoteDao
}
