package ru.napoleonit.smartmorozov.kotlinapp1

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [VitaminDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vitaminDBDao(): VitaminDBDao
}