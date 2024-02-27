package dev.mobile.medicalink.db.local.dao

import androidx.room.*
import dev.mobile.medicalink.db.local.entity.Contact


@Dao
interface ContactDao {
    @Query("SELECT * FROM Contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM Contact WHERE rpps IN (:rpps)")
    fun getById(rpps: Int): List<Contact>

    @Query("SELECT * FROM Contact WHERE uuid IN (:uuid)")
    fun getByUuid(uuid: String): List<Contact>

    @Query("SELECT * FROM Contact WHERE rpps=(:rpps) AND uuid=(:uuid)")
    fun getByIdAndUuid(rpps: Long, uuid: String): Contact

    @Query("SELECT * FROM Contact WHERE rpps IN (:rpps) AND uuid IN (:uuid)")
    fun getByIdAndUuid(rpps: Int, uuid: String): Contact

    @Insert
    fun insertAll(vararg contactDaos: Contact)

    @Delete
    fun delete(contactDao: Contact)

    @Update
    fun update(contactDao: Contact)

    @Query("DELETE FROM Contact")
    fun deleteAll()

}