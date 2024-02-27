package dev.mobile.medicalink.db.local.repository

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import dev.mobile.medicalink.db.local.dao.ContactDao
import dev.mobile.medicalink.db.local.entity.Contact

class ContactRepository(private val contactDao: ContactDao) {

    fun getAllContact(): List<Contact> {
        return try {
            contactDao.getAll()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getOneContactById(uuid: String, rpps: Long): Contact? {
        return try {
            val c = contactDao.getByIdAndUuid(rpps, uuid)
            c
        } catch (e: Exception) {
            null
        }
    }

    fun getContactsByUuid(uuid: String): List<Contact> {
        return try {
            contactDao.getByUuid(uuid)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun insertContact(contact: Contact): Pair<Boolean, String> {
        return try {
            contactDao.insertAll(contact)
            Pair(true, "Success")
        } catch (e: SQLiteConstraintException) {
            Pair(false, "Contact already exists")
        } catch (e: SQLiteException) {
            Pair(false, "Database Error : ${e.message}")
        } catch (e: Exception) {
            Pair(false, "Unknown Error : ${e.message}")
        }
    }

    fun deleteContact(contact: Contact): Pair<Boolean, String> {
        return try {
            contactDao.delete(contact)
            Pair(true, "Success")
        } catch (e: SQLiteConstraintException) {
            Pair(false, "Contact doesn't exist")
        } catch (e: SQLiteException) {
            Pair(false, "Database Error : ${e.message}")
        } catch (e: Exception) {
            Pair(false, "Unknown Error : ${e.message}")
        }
    }

    fun updateContact(contact: Contact): Pair<Boolean, String> {
        return try {
            contactDao.update(contact)
            Pair(true, "Success")
        } catch (e: SQLiteConstraintException) {
            Pair(false, "Contact doesn't exist")
        } catch (e: SQLiteException) {
            Pair(false, "Database Error : ${e.message}")
        } catch (e: Exception) {
            Pair(false, "Unknown Error : ${e.message}")
        }
    }

}