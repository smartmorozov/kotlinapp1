package ru.napoleonit.smartmorozov.kotlinapp1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.* //для байндинга элементов без findViewById
import android.view.View
import android.content.Intent

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Присваиваем кнопкам обработчик
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
    }

    //обработчик нажатий
    override fun onClick(view: View) {
        val intent = Intent()   //intent в который нужно будет перейти

        // по id определеяем кнопку, вызвавшую этот обработчик
        when(view.id) {
            R.id.button1 -> intent.setClass(this, RecyclerActivity::class.java)
            R.id.button2 -> intent.setClass(this, DatabaseActivity::class.java)
        }

        startActivity(intent)
    }

}
