package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GetDueTicketsUseCase(
    private val workTicketRepository: WorkTicketRepository
) {
    operator fun invoke(): Flow<Resource<List<Long>>> = flow {
        emit(Resource.Loading())
        workTicketRepository.getAllTickets().onEach{
            if (it is Resource.Success)
                emit(Resource.Success(it.data?.filter{ workEntity->
                    workEntity.scheduledTime < System.currentTimeMillis()
                }?.map { workEntity1->
                    workEntity1.scheduledTime
                }))
        }.collect()
    }
}