package droidkaigi.github.io.challenge2019.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import droidkaigi.github.io.challenge2019.data.db.dao.ItemDao
import droidkaigi.github.io.challenge2019.data.db.dao.ItemIdDao
import droidkaigi.github.io.challenge2019.data.db.dao.UserDao
import droidkaigi.github.io.challenge2019.data.db.entity.Item
import droidkaigi.github.io.challenge2019.data.db.entity.ItemId
import droidkaigi.github.io.challenge2019.data.db.entity.User

@Database(
    entities = [
        Item::class,
        ItemId::class,
        User::class
    ],
    version = 1
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun itemIdDao(): ItemIdDao
    abstract fun userDao(): UserDao
}
