package dev.mobile.medicalink.db.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.mobile.medicalink.db.local.entity.EffetSecondaire

@Dao
interface EffetSecondaireDao {

    @Query("SELECT * FROM EffetSecondaire")
    fun getAll(): List<EffetSecondaire>

    @Query("SELECT * FROM EffetSecondaire WHERE uuidUser IN (:uuidUser)")
    fun getByuuidUser(uuidUser: String): List<EffetSecondaire>

    @Insert
    fun insertAll(vararg effetSecondaireDaos: EffetSecondaire)

    @Delete
    fun delete(effetSecondaireDao: EffetSecondaire)

    @Update
    fun update(effetSecondaireDao: EffetSecondaire)

    @Query("DELETE FROM EffetSecondaire")
    fun deleteAll()
}