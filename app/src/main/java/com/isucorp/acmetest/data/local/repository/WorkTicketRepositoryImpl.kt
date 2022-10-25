package com.isucorp.acmetest.data.local.repository

import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.data.local.datasource.WorkTicketLocalDatasource
import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class WorkTicketRepositoryImpl(
    private val localDataSource: WorkTicketLocalDatasource
) : WorkTicketRepository {

    override fun insertWorkTicket(workTicket: WorkTicketEntity):
            Flow<Resource<WorkTicketEntity>> = flow {
        localDataSource.insertWorkTicket(workTicket).onEach { emit(it) }.collect()
    }

    override suspend fun getWorkTicket(id: Int): Resource<WorkTicketEntity> {
        return localDataSource.getWorkTicket(id)
    }

    override fun getAllTickets(): Flow<Resource<List<WorkTicketEntity>>> = flow {
        localDataSource.getAllTickets().onEach { emit(it) }.collect()
    }

    override fun updateWorkTicket(workTicket: WorkTicketEntity):
            Flow<Resource<WorkTicketEntity>> = flow {
        localDataSource.updateWorkTicket(workTicket).onEach { emit(it) }.collect()
    }

    override fun deleteWorkTicket(id: Int): Flow<Resource<Int>> = flow {
        localDataSource.deleteWorkTicket(id).onEach { emit(it) }.collect()
    }
}