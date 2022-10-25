package com.isucorp.acmetest.domain.usecase

class WorkTicketUseCase(
    val getListOfWorkTicketsUseCase: GetListOfWorkTicketsUseCase,
    val getDueTicketsUseCase: GetDueTicketsUseCase,
    val insertWorkTicketUseCase: InsertWorkTicketUseCase,
    val getWorkTicketUseCase: GetWorkTicketUseCase,
    val updateWorkTicketUseCase: UpdateWorkTicketUseCase,
    val deleteWorkTicketUseCase: DeleteWorkTicketUseCase
) {
}