package com.eburg_soft.televisionbroadcasting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.*
import org.junit.Assert.*

class GroupDaoTest : TVDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    /*
        1 GroupEntity
        -   insert,
        -   read,
        -   delete
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteGroup() {
        val resultGroups = listOf(TestUtil.TEST_GROUP_ENTITY_1)

        // insert
        getGroupDao()?.insertGroups(resultGroups)?.blockingGet()

        // read
        var insertedGroups = getGroupDao()?.getAllGroups()?.blockingFirst()
        assertNotNull(insertedGroups)
        assertEquals(resultGroups, insertedGroups)
        println(insertedGroups)
        println(resultGroups)

        // delete
        getGroupDao()?.deleteAllGroups()?.blockingGet()

        // confirm the database is empty
        insertedGroups = getGroupDao()?.getAllGroups()?.blockingFirst ()
        assertEquals(0, insertedGroups?.size)
        println(insertedGroups)
    }

    /*
        1 ChannelEntity
        -   insert,
        -   read,
        -   delete
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteChannel() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_2

        // insert
        getGroupDao()?.insertChannels(resultChannels)?.blockingGet()

        // read
        var insertedChannels = getGroupDao()?.getAllChannels()?.blockingFirst ()
        assertNotNull(insertedChannels)
//        assertEquals(resultChannels, insertedChannels)
        println(insertedChannels)
        println(resultChannels)

        // delete
        getGroupDao()?.deleteAllChannels()?.blockingGet()

        // confirm the database is empty
        insertedChannels = getGroupDao()?.getAllChannels()?.blockingFirst()
        assertEquals(0, insertedChannels?.size)
        println(insertedChannels)
    }
}