package rx.dagger.pseudo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TelegramAccountDao {
    @Query("SELECT * FROM telegramaccountentity")
    fun getAll(): List<TelegramAccountEntity>

    @Query("SELECT * FROM telegramaccountentity WHERE database_directory LIKE :databaseDirectory LIMIT 1")
    fun findByDatabaseDirectory(databaseDirectory: String): TelegramAccountEntity

    @Query("SELECT database_directory FROM telegramaccountentity")
    fun getAllDatabaseDirectories(): List<String>

    @Insert
    fun insert(tgEntity: TelegramAccountEntity)

    @Insert
    fun insertAll(vararg tgEntities: TelegramAccountEntity)

    @Delete
    fun delete(tgEntity: TelegramAccountEntity)
}