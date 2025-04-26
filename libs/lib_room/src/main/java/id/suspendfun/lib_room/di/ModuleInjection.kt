package id.suspendfun.lib_room.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.suspendfun.lib_room.dao.PokemonDao
import id.suspendfun.lib_room.dao.RemoteDao
import id.suspendfun.lib_room.db.PokemonDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleInjection {
    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(db: PokemonDatabase): PokemonDao {
        return db.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideRemoteDao(db: PokemonDatabase): RemoteDao {
        return db.remoteDao()
    }
}
