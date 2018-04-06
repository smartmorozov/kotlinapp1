package ru.napoleonit.smartmorozov.kotlinapp1

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView


class VitaminDBAdapter (
        private val vitamins: MutableList<VitaminDB>, //принимаем данные
        private val onItemClick: (View, Int) -> Unit  //и штуку, которой нужно передавать инфу о нажатиях
) : RecyclerView.Adapter<VitaminDBAdapter.ViewHolder>() {

    // Макет для элементов. Создаёт связи с вьюхами и навешивает на них слушателей нажатий.
    class ViewHolder(itemView: View, private val onItemClick: (View, Int) -> Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val cv: CardView = itemView.findViewById(R.id.vitamin_cv) as CardView
        val vitaminName: TextView = itemView.findViewById(R.id.vitamin_name)
        val vitaminDescription: TextView = itemView.findViewById(R.id.vitamin_description)

        init {
            cv.setOnClickListener(this)
            vitaminName.setOnClickListener(this)
            vitaminDescription.setOnClickListener(this)
        }

        //обработчик нажатий
        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) { //проверка, что позиция адаптера определена (для java надо будет проверять ещё не равно ли null)
                onItemClick(view, position) //передаёт инфу о нажатии (вьюху и позицию адаптера) в onItemClick функцию, полученную при создании
            }
        }
    }

    // Создание макета
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VitaminDBAdapter.ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vitamindb, parent, false) //создаём макет из xmlки
        return ViewHolder(itemView, onItemClick) //передаём макету созданную вьюху и обработчик нажатий, полученный при создании адаптера
    }

    // Контент. Связи вьюх макета с данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.vitaminName.text = vitamins[position].name
        holder.vitaminDescription.text = vitamins[position].description
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return vitamins.size
    }
}
