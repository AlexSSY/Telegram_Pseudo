package rx.dagger.pseudo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TelegramAccountEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "main.db"
            ).build()
        }
    }

    abstract fun telegramAccountDao(): TelegramAccountDao
}