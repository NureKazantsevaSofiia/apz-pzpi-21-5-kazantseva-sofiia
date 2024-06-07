package com.logihub.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.logihub.R
import com.logihub.ui.activity.truck.TruckPage

class TruckAdapter(
    var context: Context?,
    private var trucksIds: ArrayList<Long>?,
    private var numbers: ArrayList<String>?,
    private var emails: ArrayList<String>?
) : RecyclerView.Adapter<TruckAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.row_truck, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.truckId.text = trucksIds!![position].toString()
        holder.number.text = numbers!![position]
        holder.email.text = emails!![position]

        holder.view.setOnClickListener { v ->
            val txt = v.findViewById<TextView>(R.id.truckId)
            val intent = Intent(context, TruckPage::class.java)
            intent.putExtra("truckId", txt.text.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return trucksIds!!.size
    }

    class MyViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var truckId: TextView = itemView.findViewById(R.id.truckId)
        var number: TextView = itemView.findViewById(R.id.number)
        var email: TextView = itemView.findViewById(R.id.email)
        var view: View = itemView

        fun getItemView(): View {
            return view
        }
    }
}