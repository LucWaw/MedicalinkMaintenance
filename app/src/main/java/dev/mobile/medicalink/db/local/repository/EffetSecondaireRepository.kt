package dev.mobile.medicalink.db.local.repository

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import dev.mobile.medicalink.db.local.dao.EffetSecondaireDao
import dev.mobile.medicalink.db.local.entity.EffetSecondaire

class EffetSecondaireRepository(private val effetSecondaireDao: EffetSecondaireDao) {

    fun getAllEffetSecondaire(): List<EffetSecondaire> {
        return try {
            effetSecondaireDao.getAll()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getEffetSecondairesByUuid(uuid: String): List<EffetSecondaire> {
        return try {
            effetSecondaireDao.getByuuidUser(uuid)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun insertEffetSecondaire(effetSecondaire: EffetSecondaire): Pair<Boolean, String> {
        return try {
            effetSecondaireDao.insertAll(effetSecondaire)
            Pair(true, "Success")
        } catch (e: SQLiteConstraintException) {
            Pair(false, "EffetSecondaire already exists")
        } catch (e: SQLiteException) {
            Pair(false, "Database Error : ${e.message}")
        } catch (e: Exception) {
            Pair(false, "Unknown Error : ${e.message}")
        }
    }

    fun deleteEffetSecondaire(effetSecondaire: EffetSecondaire): Pair<Boolean, String> {
        return try {
            effetSecondaireDao.delete(effetSecondaire)
            Pair(true, "Success")
        } catch (e: SQLiteConstraintException) {
            Pair(false, "EffetSecondaire doesn't exist")
        } catch (e: SQLiteException) {
            Pair(false, "Database Error : ${e.message}")
        } catch (e: Exception) {
            Pair(false, "Unknown Error : ${e.message}")
        }
    }

    fun updateEffetSecondaire(effetSecondaire: EffetSecondaire): Pair<Boolean, String> {
        return try {
            effetSecondaireDao.update(effetSecondaire)
            Pair(true, "Success")
        } catch (e: SQLiteConstraintException) {
            Pair(false, "EffetSecondaire doesn't exist")
        } catch (e: SQLiteException) {
            Pair(false, "Database Error : ${e.message}")
        } catch (e: Exception) {
            Pair(false, "Unknown Error : ${e.message}")
        }
    }

}