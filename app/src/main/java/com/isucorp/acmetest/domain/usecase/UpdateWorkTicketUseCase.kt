package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class UpdateWorkTicketUseCase(
    private val workTicketRepository: WorkTicketRepository
) {

    operator fun invoke(workTicketEntity: WorkTicketEntity):
            Flow<Resource<WorkTicketEntity>> = flow {
        workTicketRepository.updateWorkTicket(workTicketEntity).onEach { emit(it) }.collect()
    }
}