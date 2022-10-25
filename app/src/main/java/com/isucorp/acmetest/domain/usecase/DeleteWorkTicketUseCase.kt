package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class DeleteWorkTicketUseCase(
    private val workTicketRepository: WorkTicketRepository
) {

    operator fun invoke(id:Int):
            Flow<Resource<Int>> = flow {
        workTicketRepository.deleteWorkTicket(id).onEach { emit(it) }.collect()
    }
}