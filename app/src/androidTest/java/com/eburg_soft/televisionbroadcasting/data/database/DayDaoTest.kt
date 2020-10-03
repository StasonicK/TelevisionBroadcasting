package com.eburg_soft.televisionbroadcasting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class DayDaoTest : TVDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    /*
        DayEntity
        -   insert,
        -   read
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDays() {
        val expectedDays = TestUtil.generateDayEntities("01.06.2020", "04.06.2020")

        // insert
        getDayDao()?.insertDays(expectedDays)?.blockingAwait()

        // read
        getDayDao()?.getAllDays()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == expectedDays
            }
    }

    /*
        DayEntity
        -   insert,
        -   read,
        -   delete
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteDays() {
        val expectedDays = TestUtil.generateDayEntities("01.01.2020", "01.02.2020")

        // insert
        getDayDao()?.insertDays(expectedDays)?.blockingAwait()

        // read
        getDayDao()?.getAllDays()
            ?.test()
            ?.assertValue { it ->
                return@assertValue it == expectedDays
            }

        // delete
        getDayDao()?.deleteAllDays()?.blockingAwait()

        // confirm the Days table is empty
        getDayDao()?.getAllDays()
            ?.test()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
    }
}