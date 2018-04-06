package ru.napoleonit.smartmorozov.kotlinapp1

import android.arch.persistence.room.*

/*Класс витаминки состоит из названия, описания и аватарки*/

@Entity(tableName = "vitamins") //класс используется как таблица в базе
data class VitaminDB (
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "description")
        var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    class List : ArrayList<VitaminDB>()
}