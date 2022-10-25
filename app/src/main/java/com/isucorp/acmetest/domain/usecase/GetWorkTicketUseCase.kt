package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.utils.Resource

class GetWorkTicketUseCase(
    private val workTicketRepository: WorkTicketRepository
) {
    suspend operator fun invoke(id: Int): Resource<WorkTicketEntity> {
        return workTicketRepository.getWorkTicket(id)
    }

}