package dev.mobile.medicalink.db.local.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.mobile.medicalink.db.local.AppDatabase
import dev.mobile.medicalink.db.local.entity.CisBdpm
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CisBdpmRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var cisBdpmRepository: CisBdpmRepository
    private val defaultCis = CisBdpm(
        11111111,
        "denomination",
        "formePharmaceutique",
        "voiesAdministration",
        "statutAdministratifAMM",
        "typeProcedureAMM",
        "etatCommercialisation",
        "dateAMM",
        "statutBdm",
        "numeroAutorisationEuropeenne",
        "titulaire",
        "surveillanceRenforcee"
    )


    @BeforeEach
    fun setupDatabase() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        cisBdpmRepository = CisBdpmRepository(db.cisBdpmDao())
    }

    @AfterEach
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun `test if we can get all cisBdpm`() {
        // Should be empty
        val cisBdpm = cisBdpmRepository.getAllCisBdpm()
        assert(cisBdpm.isEmpty())
    }

    @Test
    fun insertCisBdpmInDatabase() {
        // I prefer to create a new val cisBdpm than using defaultCis
        val cisBdpm = defaultCis
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase.size == 1)
        assert(cisBdpmFromDatabase[0].codeCIS == cisBdpm.codeCIS)
    }

    @Test
    fun insertCisBdpmInDatabaseWithSameId() {
        val cisBdpm = defaultCis
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase.size == 1)
        assert(cisBdpmFromDatabase[0].codeCIS == cisBdpm.codeCIS)
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase2 = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase2.size == 1)
        assert(cisBdpmFromDatabase2[0].codeCIS == cisBdpm.codeCIS)
    }

    @Test
    fun `update a cisBdpm`() {
        val cisBdpm = defaultCis
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase.size == 1)
        assert(cisBdpmFromDatabase[0].codeCIS == cisBdpm.codeCIS)
        val cisBdpmUpdated = cisBdpm.copy(denomination = "denominationUpdated")
        cisBdpmRepository.updateCisBdpm(cisBdpmUpdated)
        val cisBdpmFromDatabaseUpdated = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabaseUpdated.size == 1)
        assert(cisBdpmFromDatabaseUpdated[0].denomination == "denominationUpdated")
    }

    @Test
    fun `update a cisBdpm with wrong id`() {
        val cisBdpm = defaultCis
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase.size == 1)
        assert(cisBdpmFromDatabase[0].codeCIS == cisBdpm.codeCIS)
        val cisBdpmUpdated = cisBdpm.copy(codeCIS = 22222222)
        cisBdpmRepository.updateCisBdpm(cisBdpmUpdated)
        val cisBdpmFromDatabaseUpdated = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabaseUpdated.size == 1)
        assert(cisBdpmFromDatabaseUpdated[0].codeCIS == cisBdpm.codeCIS)
    }

    @Test
    fun `delete a cisBdpm`() {
        val cisBdpm = defaultCis
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase.size == 1)
        assert(cisBdpmFromDatabase[0].codeCIS == cisBdpm.codeCIS)
        cisBdpmRepository.deleteCisBdpm(cisBdpm)
        val cisBdpmFromDatabaseDeleted = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabaseDeleted.isEmpty())
    }

    @Test
    fun `delete a cisBdpm with wrong id`() {
        val cisBdpm = defaultCis
        cisBdpmRepository.insertCisBdpm(cisBdpm)
        val cisBdpmFromDatabase = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabase.size == 1)
        assert(cisBdpmFromDatabase[0].codeCIS == cisBdpm.codeCIS)
        val cisBdpmWrongId = cisBdpm.copy(codeCIS = 22222222)
        cisBdpmRepository.deleteCisBdpm(cisBdpmWrongId)
        val cisBdpmFromDatabaseDeleted = cisBdpmRepository.getOneCisBdpmById(cisBdpm.codeCIS)
        assert(cisBdpmFromDatabaseDeleted.size == 1)
        assert(cisBdpmFromDatabaseDeleted[0].codeCIS == cisBdpm.codeCIS)
    }

    @Test
    fun `test insert from csv`() {
        // Warning : this test will fail if you don't have the csv file in the assets folder
        val context = ApplicationProvider.getApplicationContext<Context>()
        cisBdpmRepository.insertFromCsv(context)
        val cisBdpmFromDatabase = cisBdpmRepository.getAllCisBdpm()
        assert(cisBdpmFromDatabase.isNotEmpty())
    }


}










