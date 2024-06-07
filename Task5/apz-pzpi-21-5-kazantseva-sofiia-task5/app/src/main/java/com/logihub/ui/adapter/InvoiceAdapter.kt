package com.logihub.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.logihub.R
import com.logihub.ui.activity.invoice.InvoicePage

class InvoiceAdapter(
    var context: Context?,
    private var invoicesIds: ArrayList<Long>?,
    private var dates: ArrayList<String>?,
    private var prices: ArrayList<Double>?
) : RecyclerView.Adapter<InvoiceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.row_invoice, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.invoiceId.text = invoicesIds!![position].toString()
        holder.date.text = dates!![position]
        holder.price.text = prices!![position].toString()

        holder.view.setOnClickListener { v ->
            val txt = v.findViewById<TextView>(R.id.invoiceId)
            val intent = Intent(context, InvoicePage::class.java)
            intent.putExtra("invoiceId", txt.text.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return invoicesIds!!.size
    }

    class MyViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var invoiceId: TextView = itemView.findViewById(R.id.invoiceId)
        var date: TextView = itemView.findViewById(R.id.date)
        var price: TextView = itemView.findViewById(R.id.price)
        var view: View = itemView

        fun getItemView(): View {
            return view
        }
    }
}