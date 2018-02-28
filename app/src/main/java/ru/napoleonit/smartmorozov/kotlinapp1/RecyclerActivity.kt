package ru.napoleonit.smartmorozov.kotlinapp1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration //для определения ориентации экрана
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler.* //для байндинга элементов без findViewById
import java.util.ArrayList

class RecyclerActivity : AppCompatActivity() {

    private var vitamins: MutableList<Vitamin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        vitamins_rv.setHasFixedSize(true)

        // layout manager
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) //если ориентация альбомная
            vitamins_rv.layoutManager = GridLayoutManager(this, 2) //используем вид в две колонки
        else //если ориентация портретная
            vitamins_rv.layoutManager = LinearLayoutManager(this) //используем линейный лейаут менеджер

        initializeData()//заполнение данных

        // specify an adapter
        vitamins_rv.adapter = VitaminAdapter(vitamins)
    }

    private fun initializeData() {
        vitamins.add(Vitamin("Витамин A", "Группа соединений ретиноидов: ретиналь (А1), дегидроретинол (А2), ретиноевая кислота.", R.drawable.vitamin_a))
        vitamins.add(Vitamin("Витамин B1", "Тиамин играет важную роль в метаболизме, мышечной, нервной и сердечной функциях.", R.drawable.vitamin_b1))
        vitamins.add(Vitamin("Витамин B2", "Участвует в регуляции процессов роста, нормализует функции внутренних желез, нужен для кроветворения, репродуктивной системы, нормализации пищеварения.", R.drawable.vitamin_b2))
        vitamins.add(Vitamin("Витамин B4", "Витаминоподобное вещество холин, вступает в активные связи со многими витаминами, микроэлементами и аминокислотами, запускает сложнейшие процессы обмена веществ, регулирует работу ключевых систем.", R.drawable.vitamin_b4))
        vitamins.add(Vitamin("Витамин B5", "Пантотеновая кислота участвует в метаболизме, процессах синтеза энергии, регенерации клеток, улучшает состояние иммунной системы", R.drawable.vitamin_b5))
        vitamins.add(Vitamin("Витамин B6", "Пиридоксин активно участвует во многих процессах: ферментации, синтеза аминокислот и гормонов, нормализации действия внутренних систем организма.", R.drawable.vitamin_b6))
        vitamins.add(Vitamin("Витамин B8", "Инозитол контролирует обменные процессы в организме, в частности усвоение жиров. ", R.drawable.vitamin_b8))
        vitamins.add(Vitamin("Витамин B9", "Фолиевая кислота уменьшает проявление анемии, обеспечивает нормальную работу нервной системы.\nПринимает участие в синтезе некоторых гормонов, в частности норадреналина и серотонина, которые отвечают за работу сердца и сосудов, тонус желудочно-кишечного тракта, сопротивляемость стрессам, хорошее настроение и нормальный сон.\nНеобходима для синтеза аминокислот метионина и гомоцистеина. Эти аминокислоты незаменимы. При их недостатке возрастает риск повреждения кровеносных сосудов и образования тромбов, развития инсульта. \nПри участии синтезируются и аминокислоты ДНК, РНК.", R.drawable.vitamin_b9))
        vitamins.add(Vitamin("Витамин B12", "Учавствует в синтезе крови", R.drawable.vitamin_b12))
        vitamins.add(Vitamin("Витамин B13", "Регулирует работу печени, обеспечивает ее защиту, препятствует ожирению.", R.drawable.vitamin_b13))
        vitamins.add(Vitamin("Витамин B15", "Пангамовая кислота повышает устойчивость тканей организма к недостатку кислорода. При применении улучшается работоспособность и замедляется процесс разрушения клеток.", R.drawable.vitamin_b15))
        vitamins.add(Vitamin("Витамин C", "Органическое соединение с формулой C6H8O6, является одним из основных веществ в человеческом рационе, которое необходимо для нормального функционирования соединительной и костной ткани", R.drawable.vitamin_c))
        vitamins.add(Vitamin("Витамин D", "Необходим для нормального образования и роста костей, \nрегулирует обмен кальция и фосфора. \nСпособствует нормальной работе сердца, свёртыванию крови.", R.drawable.vitamin_d))
        vitamins.add(Vitamin("Витамин E", "Группа природных соединений производных токола. Важнейшими соединениями являются токоферолы и токотриенолы.", R.drawable.vitamin_e))
        vitamins.add(Vitamin("Витамин H", "Функции биотина связаны с участием в обмене глюкозы, образования жирных кислот, а также метаболизме аминокислот.", R.drawable.vitamin_h))
        vitamins.add(Vitamin("Витамин K", "Играет значительную роль в обмене веществ в костях и в соединительной ткани, а также в здоровой работе почек.", R.drawable.vitamin_k))
    }
}