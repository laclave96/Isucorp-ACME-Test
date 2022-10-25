package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class InsertWorkTicketUseCase(
    private val workTicketRepository: WorkTicketRepository
) {

    operator fun invoke(workTicketEntity: WorkTicketEntity):
            Flow<Resource<WorkTicketEntity>> = flow {
        workTicketRepository.insertWorkTicket(workTicketEntity).onEach { emit(it) }.collect()

    }
}