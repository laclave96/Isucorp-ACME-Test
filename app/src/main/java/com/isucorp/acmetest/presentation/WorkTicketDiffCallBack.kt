package com.isucorp.acmetest.presentation

import androidx.recyclerview.widget.DiffUtil
import com.isucorp.acmetest.presentation.model.WorkTicketItem

class WorkTicketDiffCallBack(
    private val oldList: List<WorkTicketItem>, private val newList: List<WorkTicketItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, value, name) = oldList[oldItemPosition]
        val (_, value1, name1) = newList[newItemPosition]
        return name == name1 && value == value1

    }


}