package ru.napoleonit.smartmorozov.kotlinapp1

import android.content.Context
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
import android.net.ConnectivityManager //для проверки подключений
import okhttp3.OkHttpClient //okhttp для работы с сетью
import okhttp3.Request
import okhttp3.Response
import com.google.gson.Gson //gson для работы с json

class OkhttpActivity : AppCompatActivity() {

    val job = Job() // работа всех корутин

    private val vitamins = VitaminDB.List()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)

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
            //проверка наличия подключения
            val myConnMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //менеджер подключений
            val networkinfo = myConnMgr.activeNetworkInfo//узнаём инфу о сети
            if (networkinfo != null && networkinfo.isConnected) { //если есть подключение
                val cloudVitamins = async(coroutineContext + CommonPool) { //получаем витамины из сети (получаем в фоне, но с родительским контекстом для управления)

                    // Создаём клиент для HTTP запросов
                    val httpClient = OkHttpClient()

                    // Создаём запрос
                    val request = Request.Builder()
                            .url("http://176.226.158.29:8080/")//176.226.158.29   192.168.1.244 //TODO указать действующий адрес (возможно вынести в константы или в строки, ещё вариант создать экран с настройками)
                            .build()
                    try {
                        val response = httpClient.newCall(request).execute()
                        val responseText = response.body()!!.string() //TODO возможно сделать проверку что витамины не пусты и бахнуть вывод отдельного сообщения об этом
                        Gson().fromJson(responseText, VitaminDB.List::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace();
                        return@async null
                    }
                }.await() //дожидаемся получения
                if(cloudVitamins!=null) {
                    vitamins.addAll(cloudVitamins) //наполняем данными из базы текущий список
                    vitamins_rv.adapter.notifyDataSetChanged() //уведомляем адаптер, что данные изменились
                } else {
                    Toast.makeText(applicationContext, R.string.request_fail, Toast.LENGTH_LONG).show()//выводим сообщение об ошибке //TODO можно потом заменить тосты на нижние шторки
                }
            } else {
                Toast.makeText(applicationContext, R.string.no_connection, Toast.LENGTH_LONG).show()//говорим, что нет Инета //TODO можно потом заменить тосты на нижние шторки
            }
        }
    }

    override fun onDestroy() { //при уничтожении активити
        super.onDestroy()
        job.cancel() //завершение всех корутин
    }
}