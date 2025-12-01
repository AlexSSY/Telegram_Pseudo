package rx.dagger.pseudo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TelegramAccountEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "database_directory") val databaseDirectory: String,
)
