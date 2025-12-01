package rx.dagger.pseudo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TelegramAccountDao {
    @Query("SELECT * FROM telegramaccountentity")
    fun getAllAccountsFlow(): Flow<List<TelegramAccountEntity>>

    @Query("SELECT * FROM telegramaccountentity WHERE database_directory LIKE :databaseDirectory LIMIT 1")
    fun findByDatabaseDirectoryFlow(databaseDirectory: String): Flow<TelegramAccountEntity>

    @Query("SELECT database_directory FROM telegramaccountentity")
    fun getAllDatabaseDirectoriesFlow(): Flow<List<String>>

    @Insert
    suspend fun insert(tgEntity: TelegramAccountEntity)

    @Insert
    suspend fun insertAll(vararg tgEntities: TelegramAccountEntity)

    @Delete
    suspend fun delete(tgEntity: TelegramAccountEntity)
}