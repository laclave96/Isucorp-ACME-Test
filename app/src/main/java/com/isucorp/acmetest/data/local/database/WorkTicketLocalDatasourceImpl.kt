package com.isucorp.acmetest.data.local.database

import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.database.dao.WorkTicketDao
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.data.local.datasource.WorkTicketLocalDatasource
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.*
import java.lang.Exception

class WorkTicketLocalDatasourceImpl(
    private val workTicketDao: WorkTicketDao
) : WorkTicketLocalDatasource {

    override fun insertWorkTicket(workTicket: WorkTicketEntity):
            Flow<Resource<WorkTicketEntity>> = flow {
        try {
            workTicketDao.insertWorkTicket(workTicket)
        } catch (e: Exception) {
            emit(Resource.Error<WorkTicketEntity>(resId = R.string.insert_work_ticket_error))
        }
        emit(Resource.Success(workTicket))
    }

    override suspend fun getWorkTicket(id: Int): Resource<WorkTicketEntity> {
        val result = workTicketDao.getWorkTicket(id)
        return result?.let { Resource.Success(it) }
            ?: Resource.Error<WorkTicketEntity>(resId = R.string.fetch_ticket_info_error)
    }

    override fun getAllTickets(): Flow<Resource<List<WorkTicketEntity>>> = flow {
        workTicketDao.getAll().onEach {
            emit(it?.let { Resource.Success(it) }
                ?: Resource.Error<List<WorkTicketEntity>>(resId = R.string.fetch_work_tickets_error))
        }.collect()

    }

    override fun updateWorkTicket(workTicket: WorkTicketEntity):
            Flow<Resource<WorkTicketEntity>> = flow {

        try {
            workTicketDao.updateWorkTicket(workTicket)
        } catch (e: Exception) {
            emit(Resource.Error<WorkTicketEntity>(resId = R.string.update_work_ticket_error))
        }
        emit(Resource.Success(workTicket))
    }

    override fun deleteWorkTicket(id: Int):
            Flow<Resource<Int>> = flow {

        try {
            workTicketDao.deleteWorkTicket(id)
        } catch (e: Exception) {
            emit(Resource.Error<Int>(R.string.delete_work_ticket_error))
        }
        emit(Resource.Success(id))
    }

}