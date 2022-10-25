package com.isucorp.acmetest.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.databinding.DialogWorkTicketBinding
import com.isucorp.acmetest.utils.DateFormat
import java.util.*

class WorkTicketDialog(context: Context, workTicketEntity: WorkTicketEntity?) :
    BottomSheetDialog(context), DatePickerDialog.OnSaveClickListener {

    private val binding: DialogWorkTicketBinding =
        DialogWorkTicketBinding.inflate(LayoutInflater.from(context))
    private var saveListener: OnSaveClickListener? = null
    private var scheduledDate: Date? = null

    interface OnSaveClickListener {
        fun onSaveClick(workTicketEntity: WorkTicketEntity)
    }

    fun setOnSaveClickListener(listener: OnSaveClickListener) {
        saveListener = listener
    }

    init {
        setCancelable(true)
        setContentView(binding.root)
        binding.workTicket = workTicketEntity

        val strDate = binding.workTicket?.let {
            DateFormat.getString(it.scheduledTime, "dd/MM/yy")
        } ?: context.getString(R.string.select_date)
        binding.textScheduledTime.text = strDate
        val actualScheduledDate = binding.workTicket?.let { Date(it.scheduledTime) }

        binding.textScheduledTime.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, actualScheduledDate)
            datePickerDialog.setOnSaveClickListener(this)
            datePickerDialog.show()
        }

        binding.saveBtn.setOnClickListener {

            saveListener?.onSaveClick(getWorkTicket())
        }
        setOnCancelListener { dismiss() }
    }


    private fun getWorkTicket(): WorkTicketEntity {
        return WorkTicketEntity(
            binding.workTicket?.let { it.id } ?: 0,
            binding.editTextCustomerName.text?.trim().toString(),
            binding.editTextPhoneNumber.text?.trim().toString().toInt(),
            scheduledDate?.time ?: binding.workTicket?.scheduledTime
            ?: System.currentTimeMillis(),
            binding.editTextJobStreet.text?.trim().toString(),
            binding.editTextJobCity.text?.trim().toString(),
            binding.editTextPostalCode.text?.trim().toString(),
            binding.editTextFee.text?.trim().toString().toInt(),
            binding.editTextJobDescription.text?.trim().toString(),
            binding.editTextDeptClass.text?.trim().toString(),
            binding.editTextServiceType.text?.trim().toString(),
            binding.editTextReasonForCall.text?.trim().toString()
        )
    }

    override fun onSaveClick(newDate: Date?) {
        scheduledDate = newDate
        val strDate = scheduledDate?.let {
            DateFormat.getString(it.time, "dd/MM/yy")
        }
        binding.textScheduledTime.text = "Scheduled For: " + strDate
    }
}

