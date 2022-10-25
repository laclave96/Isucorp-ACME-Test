package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.presentation.model.WorkTicketItem
import com.isucorp.acmetest.utils.DateFormat
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.*

class GetListOfWorkTicketsUseCase(
    private val workTicketRepository: WorkTicketRepository
) {

    operator fun invoke(): Flow<Resource<List<WorkTicketItem>>> = flow {
        emit(Resource.Loading())
        workTicketRepository.getAllTickets().onEach {
            val result: Resource<List<WorkTicketItem>> = if (it is Resource.Success) {
                Resource.Success(it.data?.map { convert(it) })
            } else {
                Resource.Error(resId = R.string.fetch_work_tickets_error)
            }
            emit(result)
        }.collect()
    }

    private fun convert(workTicket: WorkTicketEntity): WorkTicketItem {
        return WorkTicketItem(
            workTicket.id,
            DateFormat.getString(workTicket.scheduledTime, "hh:mm a"),
            DateFormat.getString(workTicket.scheduledTime, "dd/MM/yy"),
            workTicket.serviceType, workTicket.jobStreet, workTicket.jobCity,
            workTicket.jobPostalCode
        )
    }
}