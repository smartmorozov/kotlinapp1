package ru.napoleonit.smartmorozov.kotlinapp1

import android.view.View

//интерфейс принятия инфы о нажатиях от RecyclerView в основной Activity
interface RecyclerViewClickListener {
    fun onClick(view: View, position: Int)
}