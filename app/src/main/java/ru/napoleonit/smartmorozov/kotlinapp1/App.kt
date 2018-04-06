package ru.napoleonit.smartmorozov.kotlinapp1

import android.app.Application
import android.arch.persistence.room.Room

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "vitamin_database").build()
    }
}