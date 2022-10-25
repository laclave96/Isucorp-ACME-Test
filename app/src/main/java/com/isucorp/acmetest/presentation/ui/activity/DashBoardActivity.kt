package com.isucorp.acmetest.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.databinding.DashboardActivityBinding
import com.isucorp.acmetest.presentation.adapters.WorkTicketAdapter
import com.isucorp.acmetest.presentation.ui.WorkTicketDialog
import com.isucorp.acmetest.presentation.viewmodel.DashBoardViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class DashBoardActivity : AppCompatActivity(), WorkTicketDialog.OnSaveClickListener,
    WorkTicketAdapter.OnClickTicketListener {

    private lateinit var binding: DashboardActivityBinding
    private val dashBoardViewModel: DashBoardViewModel by viewModel()
    private lateinit var workTicketAdapter: WorkTicketAdapter
    private var dialog: WorkTicketDialog? = null
    private var saveAction = SaveAction.INSERT

    enum class SaveAction {
        INSERT, UPDATE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupRecyclerView()
        subscribeToObservables()

    }

    private fun init() {
        binding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = dashBoardViewModel
    }

    private fun setupRecyclerView() {
        workTicketAdapter = WorkTicketAdapter()
        workTicketAdapter.setOnClickListener(this)
        val recyclerView = binding.ticketRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = workTicketAdapter
    }

    private fun subscribeToObservables() {
        dashBoardViewModel.workTickets.observe(this) {
            workTicketAdapter.setData(it)
        }
        dashBoardViewModel.dueTickets.observe(this) {
            val events: MutableList<EventDay> = ArrayList()
            val calendar = Calendar.getInstance()

            it.forEach { time ->
                calendar.timeInMillis = time
                events.add(EventDay(calendar,R.drawable.ic_baseline_work_24))
            }
            val calendarView: CalendarView =  binding.calendarView as CalendarView
            calendarView.setEvents(events)

        }
        dashBoardViewModel.workTicket.observe(this) {
            dialog = WorkTicketDialog(this, it)
            dialog?.setOnSaveClickListener(this)
            dialog?.show()
        }
        dashBoardViewModel.saveSuccess.observe(this) {
            if (it == true)
                dialog?.dismiss()
        }
        lifecycleScope.launchWhenStarted {
            dashBoardViewModel.uiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is DashBoardViewModel.UiEvent.ShowMessage -> {
                        Toast.makeText(
                            this@DashBoardActivity,
                            getString(uiEvent.resId ?: R.string.unknown_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    fun addTicket(view: View) {
        saveAction = SaveAction.INSERT
        dialog = WorkTicketDialog(this, null)
        dialog?.setOnSaveClickListener(this)
        dialog?.show()
    }

    fun syncData(view: View) {

    }

    fun showDueTickets(view: View) {
         val calendarView = binding.calendarView as CalendarView
         if (calendarView.isVisible) {
             calendarView.visibility = View.GONE
             return
         }
         calendarView.visibility = View.VISIBLE
         dashBoardViewModel.loadDueTickets()
    }


    override fun onSaveClick(workTicketEntity: WorkTicketEntity) {
        if (saveAction == SaveAction.INSERT)
            dashBoardViewModel.insertWorkTicket(workTicketEntity)
        else
            dashBoardViewModel.updateWorkTicket(workTicketEntity)
    }

    override fun onViewTicketClick(id: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("ticket_id", id)
        startActivity(intent)
    }

    override fun onUpdateTicketClick(id: Int) {
        saveAction = SaveAction.UPDATE
        dashBoardViewModel.getWorkTicket(id)
    }

    override fun onDeleteTicketClick(id: Int) {
        dashBoardViewModel.deleteTicket(id)
    }
}