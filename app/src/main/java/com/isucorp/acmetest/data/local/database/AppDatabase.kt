package com.isucorp.acmetest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isucorp.acmetest.data.local.database.dao.WorkTicketDao
import com.isucorp.acmetest.data.local.model.WorkTicketEntity

@Database(entities = [WorkTicketEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workTicketDao(): WorkTicketDao

    /*companion object {
        @Volatile
        private var sINSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return sINSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )   .addCallback(object:Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        }
                    }
                })
                    .build()
                sINSTANCE = instance
                return instance
            }
        }
    }*/
}