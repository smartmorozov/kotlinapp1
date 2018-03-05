package ru.napoleonit.smartmorozov.kotlinapp1

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.widget.ImageView


class VitaminAdapter (
        private val vitamins: MutableList<Vitamin>, //принимаем данные
        private val onItemClick: (View, Int) -> Unit  //и штуку, которой нужно передавать инфу о нажатиях
) : RecyclerView.Adapter<VitaminAdapter.ViewHolder>() {

    // Макет для элементов. Создаёт связи с вьюхами и навешивает на них слушателей нажатий.
    class ViewHolder(itemView: View, private val onItemClick: (View, Int) -> Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val cv: CardView = itemView.findViewById(R.id.vitamin_cv) as CardView
        val vitaminName: TextView = itemView.findViewById(R.id.vitamin_name)
        val vitaminDescription: TextView = itemView.findViewById(R.id.vitamin_description)
        val vitaminIv: ImageView = itemView.findViewById(R.id.vitamin_iv)

        init {
            cv.setOnClickListener(this)
            vitaminName.setOnClickListener(this)
            vitaminDescription.setOnClickListener(this)
            vitaminIv.setOnClickListener(this)
        }

        //обработчик нажатий
        override fun onClick(view: View) {
            onItemClick(view, adapterPosition) //передаёт инфу о нажатии (вьюху и позицию адаптера) в onItemClick функцию, полученную при создании
        }
    }

    // Создание макета
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VitaminAdapter.ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vitamin, parent, false) //создаём макет из xmlки
        return ViewHolder(itemView, onItemClick) //передаём макету созданную вьюху и обработчик нажатий, полученный при создании адаптера
    }

    // Контент. Связи вьюх макета с данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.vitaminName.text = vitamins[position].name
        holder.vitaminDescription.text = vitamins[position].description
        holder.vitaminIv.setImageResource(vitamins[position].avatar)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return vitamins.size
    }
}
