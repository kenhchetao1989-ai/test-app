package com.example.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayAdapter(
    private val items: List<CalendarDay>,
    private val onDayClick: (CalendarDay) -> Unit
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSolarDay: TextView = itemView.findViewById(R.id.tvSolarDay)
        val tvLunarDay: TextView = itemView.findViewById(R.id.tvLunarDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.day_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = items[position]

        holder.tvSolarDay.text = item.day.toString()
        holder.tvLunarDay.text = item.lunarDay

        val isCurrentMonth = item.isCurrentMonth
        val isSelected = position == selectedPosition

        holder.itemView.isSelected = isSelected
        holder.itemView.alpha = if (isCurrentMonth) 1f else 0.45f

        if (!isCurrentMonth) {
            holder.tvSolarDay.setTextColor(holder.itemView.context.getColor(android.R.color.darker_gray))
            holder.tvLunarDay.setTextColor(holder.itemView.context.getColor(android.R.color.darker_gray))
        } else {
            holder.tvSolarDay.setTextColor(holder.itemView.context.getColor(android.R.color.black))
            holder.tvLunarDay.setTextColor(holder.itemView.context.getColor(R.color.teal_700))
        }

        if (isSelected) {
            holder.itemView.setBackgroundResource(R.drawable.day_bg)
        } else {
            holder.itemView.setBackgroundResource(android.R.color.transparent)
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onDayClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
