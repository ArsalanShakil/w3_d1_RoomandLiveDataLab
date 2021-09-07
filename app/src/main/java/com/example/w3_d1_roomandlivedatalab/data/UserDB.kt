package com.example.w3_d1_roomandlivedatalab.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(User::class), (ContactInfo::class)],
    version = 1)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun contactDao(): ContactInfoDao

    companion object{
        private var sInstance: UserDB? = null
        @Synchronized
        fun get(context: Context): UserDB {
            if (sInstance == null) {
                sInstance =
                    Room.databaseBuilder(context.applicationContext,
                        UserDB::class.java, "users.db").build()
            }
            return sInstance!!
        }
    }
}