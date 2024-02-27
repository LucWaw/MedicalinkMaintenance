package dev.mobile.medicalink.db.local

import androidx.test.core.app.ApplicationProvider
import org.junit.jupiter.api.Test


//@Config(sdk = [29])
//@SmallTest
class AppDatabaseTest {

    @Test
    fun `test singleton`() {
        val db1 = AppDatabase.getInstance(ApplicationProvider.getApplicationContext())
        val db2 = AppDatabase.getInstance(ApplicationProvider.getApplicationContext())
        assert(db1 === db2)
    }

}