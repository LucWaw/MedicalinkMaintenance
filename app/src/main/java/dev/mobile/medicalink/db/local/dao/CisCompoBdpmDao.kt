package dev.mobile.medicalink.db.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.mobile.medicalink.db.local.entity.CisCompoBdpm

@Dao
interface CisCompoBdpmDao {
    @Query("SELECT * FROM CisCompoBdpm")
    fun getAll(): List<CisCompoBdpm>

    @Query("SELECT * FROM CisCompoBdpm WHERE codeCIS IN (:codeCIS)")
    fun getById(codeCIS: Int): List<CisCompoBdpm>

    @Insert
    fun insertAll(vararg cisCompoBdpmDaos: CisCompoBdpm)

    @Delete
    fun delete(cisCompoBdpmDao: CisCompoBdpm)

    @Update
    fun update(cisCompoBdpmDao: CisCompoBdpm)

    @Query("DELETE FROM CisCompoBdpm")
    fun deleteAll()
}