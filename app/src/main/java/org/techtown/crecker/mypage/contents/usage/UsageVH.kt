package org.techtown.crecker.mypage.contents.usage

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.techtown.crecker.R
import java.text.SimpleDateFormat


class UsageVH(view : View) : RecyclerView.ViewHolder(view){
    private val tvTitle : TextView = view.findViewById(R.id.usage_title)
    private val tvIO : TextView = view.findViewById(R.id.usage_io)
    private val tvMoney : TextView = view.findViewById(R.id.usage_money)
    private val tvDate : TextView = view.findViewById(R.id.usage_date)

    fun bind(data : CashData.Data.History) {
        tvTitle.text = data.title
        tvIO.text = data.isIn

        if(data.isIn == "적립"){
            tvMoney.text = "+${data.price}"
            tvMoney.setTextColor(itemView.context.resources.getColor(R.color.plus))
        }
        else if(data.isIn == "출금"){
            tvMoney.text = "-${data.price}"
            tvMoney.setTextColor(itemView.context.resources.getColor(R.color.minus))
        }

        tvDate.text = data.date
    }
}