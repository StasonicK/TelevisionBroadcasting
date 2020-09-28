package com.eburg_soft.televisionbroadcasting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.*
import org.junit.Assert.*

class ChannelDaoTest : TVDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

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
        getChannelDao()?.insertChannels(resultChannels)?.blockingGet()

        // read
        var insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
        assertNotNull(insertedChannels)
        assertEquals(resultChannels, insertedChannels)
        println(insertedChannels)
        println(resultChannels)

        // delete
        getChannelDao()?.deleteAllChannels()?.blockingGet()

        // confirm the database is empty
        insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
//        assertEquals(0, insertedChannels?.size)
        println(insertedChannels)
        println(insertedChannels)
    }

    /*
        ChannelEntity
        -   insert,
        -   read,
        -   delete
     */
    @Test
    @Throws(Exception::class)
    fun insertReadChannelByGroupIdDeleteChannel() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_2
        val groupIds: ArrayList<String> = ArrayList()
        resultChannels.forEach { it ->
            groupIds.add(it.groupId)
        }

        // insert
        getChannelDao()?.insertChannels(resultChannels)?.blockingGet()

        // read
        var insertedChannels = getChannelDao()?.getChannelByGroupId(groupIds[0])?.blockingFirst()
        assertNotNull(insertedChannels)
        assertEquals(resultChannels, insertedChannels)
        println(insertedChannels)
        println(resultChannels)

        // delete
        getChannelDao()?.deleteAllChannels()?.blockingGet()

        // confirm the database is empty
        insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
//        assertEquals(0, insertedChannels?.size)
        println(insertedChannels)
        println(insertedChannels)
    }
}