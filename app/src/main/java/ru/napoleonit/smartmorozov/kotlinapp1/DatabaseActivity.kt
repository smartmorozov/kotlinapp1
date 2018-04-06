package ru.napoleonit.smartmorozov.kotlinapp1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration //для определения ориентации экрана
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler.* //для байндинга элементов без findViewById
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.experimental.* //корутины
import kotlinx.coroutines.experimental.android.*

class DatabaseActivity : AppCompatActivity() {

    val job = Job() // работа всех корутин

    private val vitamins = VitaminDB.List()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        vitamins_rv.setHasFixedSize(true)

        // layout manager
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) //если ориентация альбомная
            vitamins_rv.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager //используем вид в две колонки
        else //если ориентация портретная
            vitamins_rv.layoutManager = LinearLayoutManager(this) //используем линейный лейаут менеджер

        // штука, которая запускается при нажатиях на элементы списка
        val onItemClick = { view: View, position: Int -> //знает о том какой элемент нажали и что именно в нём
            if (vitamins.size > 0 && position >= 0 && position < vitamins.size  ) { //проверка наличия позиции в массиве и существования элементов в массиве
                val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT) //переменная для отображаемого сообщения
                when(view.id) { //определяем по какой вьюхе в элементе тыкнули
                    R.id.vitamin_cv,
                    R.id.vitamin_name -> toast.setText(vitamins[position].name) //добавляем к сообщению значения из конкретного элемента
                    R.id.vitamin_description -> toast.setText(vitamins[position].description)
                }
                toast.show() //отображаем сообщение
            }
        }

        // specify an adapter
        vitamins_rv.adapter = VitaminDBAdapter(vitamins, onItemClick) //при создании адаптера передаём в него данные и обработчик нажатий

        launch(UI, parent = job) {//корутина выполняющаяся в UI потоке, с общим родителем Джобом, чтобы управлять её завершением
            val cachedVitamins = async(coroutineContext + CommonPool) { //получаем витамины из базы (получаем в фоне, но с родительским контекстом для управления)
                App.instance.database.vitaminDBDao().all //все записи из базы
            }.await() //дожидаемся получения
            if (cachedVitamins.isNotEmpty()) { //если получили список с элементами
                vitamins.addAll(cachedVitamins) //наполняем данными из базы текущий список
            } else { //если список пустой
                val databaseJob = launch(coroutineContext + CommonPool) { //запуск в фоновых потоках с родительским контекстом для управления
                    App.instance.database.vitaminDBDao().firstData() //запускаем заполнение базы первыми значениями
                }
                databaseJob.join() //ждём завершения работы с базой
                val firstVitamins = async(coroutineContext + CommonPool) { //получаем созданные данные (получаем в фоне, но с родительским контекстом для управления)
                    App.instance.database.vitaminDBDao().all //все записи из базы
                }.await() //дожидаемся получения
                vitamins.addAll(firstVitamins) //наполняем данными из базы текущий список
            }
            vitamins_rv.adapter.notifyDataSetChanged() //уведомляем адаптер, что данные изменились
        }
    }

    override fun onDestroy() { //при уничтожении активити
        super.onDestroy()
        job.cancel() //завершение всех корутин
    }
}