package com.isucorp.acmetest.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.isucorp.acmetest.R
import com.isucorp.acmetest.presentation.WorkTicketDiffCallBack
import com.isucorp.acmetest.presentation.model.WorkTicketItem
import kotlin.collections.ArrayList

class WorkTicketAdapter() : RecyclerView.Adapter<WorkTicketAdapter.WorkTicketViewHolder>() {

    private val tickets = ArrayList<WorkTicketItem>()
    private var onClickTicketListener: OnClickTicketListener? = null

    interface OnClickTicketListener {
        fun onViewTicketClick(id: Int)
        fun onUpdateTicketClick(id: Int)
        fun onDeleteTicketClick(id: Int)
    }

    fun setOnClickListener(ticketListener: OnClickTicketListener) {
        onClickTicketListener = ticketListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkTicketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_ticket, parent, false)
        return WorkTicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkTicketViewHolder, position: Int) {

        val ticket = tickets[position]
        holder.timeTv.text = ticket.time
        holder.dateTv.text = ticket.date
        holder.tickerNumberTv.text = ""+ticket.id
        holder.serviceTypeTv.text = ticket.serviceType
        holder.streetTv.text = ticket.jobStreet
        holder.cityTv.text = ticket.jobCity
        holder.postalCodeTv.text = ticket.jobPostalCode

        holder.viewTicketBtn.setOnClickListener {
            onClickTicketListener?.onViewTicketClick(ticket.id)
        }
        holder.updateBtn.setOnClickListener {
            onClickTicketListener?.onUpdateTicketClick(ticket.id)
        }
        holder.deleteBtn.setOnClickListener {
            onClickTicketListener?.onDeleteTicketClick(ticket.id)
        }
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    fun setData(newTickets: List<WorkTicketItem>) {
        val diffCallback = WorkTicketDiffCallBack(tickets, newTickets)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tickets.clear()
        tickets.addAll(newTickets)
        diffResult.dispatchUpdatesTo(this)
    }

    class WorkTicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val timeTv: TextView = itemView.findViewById(R.id.time_text)
        val dateTv: TextView = itemView.findViewById(R.id.date_text)
        val tickerNumberTv: TextView = itemView.findViewById(R.id.ticker_number_text)
        val serviceTypeTv: TextView = itemView.findViewById(R.id.service_type_text)
        val streetTv: TextView = itemView.findViewById(R.id.street_text)
        val cityTv: TextView = itemView.findViewById(R.id.city_text)
        val postalCodeTv: TextView = itemView.findViewById(R.id.postal_code_text)
        val viewTicketBtn: Button = itemView.findViewById(R.id.view_ticket_btn)
        val updateBtn: ImageView = itemView.findViewById(R.id.update_btn)
        val deleteBtn: ImageView = itemView.findViewById(R.id.delete_btn)

    }
}