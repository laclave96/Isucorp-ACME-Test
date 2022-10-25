package com.isucorp.acmetest.domain.repository
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WorkTicketRepository {

    fun insertWorkTicket(workTicket: WorkTicketEntity):Flow<Resource<WorkTicketEntity>>

    suspend fun getWorkTicket(id:Int):Resource<WorkTicketEntity>

    fun getAllTickets():Flow<Resource<List<WorkTicketEntity>>>

    fun updateWorkTicket(workTicket: WorkTicketEntity):Flow<Resource<WorkTicketEntity>>

    fun deleteWorkTicket(id: Int):Flow<Resource<Int>>


}