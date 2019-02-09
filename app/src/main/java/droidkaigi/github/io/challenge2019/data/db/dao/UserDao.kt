package droidkaigi.github.io.challenge2019.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import droidkaigi.github.io.challenge2019.data.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>


}
