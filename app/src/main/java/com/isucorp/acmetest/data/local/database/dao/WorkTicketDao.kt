package com.isucorp.acmetest.data.local.database.dao

import androidx.room.*
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkTicketDao {

    @Insert
    suspend fun insertWorkTicket(workTicket: WorkTicketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBulk(workTickets: List<WorkTicketEntity>)

    @Update
    suspend fun updateWorkTicket(workTicket: WorkTicketEntity)

    @Query("DELETE FROM work_ticket WHERE id =:id")
    suspend fun deleteWorkTicket(id: Int)

    @Query("DELETE FROM work_ticket ")
    suspend fun deleteAll()

    @Query("SELECT * FROM work_ticket WHERE id =:id")
    fun getWorkTicket(id:Int): WorkTicketEntity?

    @Query("SELECT * FROM work_ticket ORDER BY scheduled_time ASC")
    fun getAll(): Flow<List<WorkTicketEntity>?>
}