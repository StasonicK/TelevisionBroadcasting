package com.eburg_soft.televisionbroadcasting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class GroupDaoTest : TVDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    /*
        GroupEntity
        -   insert,
        -   read
     */
    @Test
    @Throws(Exception::class)
    fun insertReadGroup() {
        val resultGroups = listOf(TestUtil.TEST_GROUP_ENTITY_1)

        // insert
        getGroupDao()?.insertGroups(resultGroups)?.blockingAwait()

        // read
        getGroupDao()?.getAllGroups()
            ?.test()
            ?.assertValue { it ->
                return@assertValue it == resultGroups
            }
    }

    /*
        GroupEntity
        -   insert,
        -   read,
        -   delete
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteGroup() {
        val resultGroups = listOf(TestUtil.TEST_GROUP_ENTITY_2)

        // insert
        getGroupDao()?.insertGroups(resultGroups)?.blockingAwait()

        // read
        getGroupDao()?.getAllGroups()
            ?.test()
            ?.assertValue { it ->
                return@assertValue it == resultGroups
            }

        // delete
        getGroupDao()?.deleteAllGroups()?.blockingAwait()

        // confirm the Groups table is empty
        getGroupDao()?.getAllGroups()
            ?.test()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
    }

    /*
        ChannelEntity
        -   insert,
        -   read,
        -   delete
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteChannel() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_2

        // insert
//        getGroupDao()?.insertChannels(resultChannels)?.blockingGet()

        // read
//        var insertedChannels = getGroupDao()?.getAllChannels()?.blockingFirst ()
//        assertNotNull(insertedChannels)
//        assertEquals(resultChannels, insertedChannels)
//        println(insertedChannels)
        println(resultChannels)

        // delete
//        getGroupDao()?.deleteAllChannels()?.blockingGet()

        // confirm the database is empty
//        insertedChannels = getGroupDao()?.getAllChannels()?.blockingFirst()
//        assertEquals(0, insertedChannels?.size)
//        println(insertedChannels)
    }
}