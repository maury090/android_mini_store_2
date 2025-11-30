package com.example.android_mini_store.data.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UsuarioEntity::class],
    version = 2,  // 🆕 VERSIÓN 2
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mini_store_db"
                )
                    .fallbackToDestructiveMigration() // 🆕 SOLUCIÓN AL ERROR DE MIGRACIÓN
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // 🆕 CREAR USUARIO ADMIN AL CREAR LA BD
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).usuarioDao().crearUsuarioAdminInicial()
                            }
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // 🆕 VERIFICAR TAMBIÉN AL ABRIR LA BD (POR SI ACASO)
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    getInstance(context).usuarioDao().crearUsuarioAdminInicial()
                                } catch (e: Exception) {
                                    println("❌ [DATABASE] Error en onOpen: ${e.message}")
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: getDatabase(context)
        }
    }
}