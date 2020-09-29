package com.eburg_soft.televisionbroadcasting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class ChannelDaoTest : TVDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    /*
        ChannelEntity
        -   insert,
        -   read
     */
    @Test
    @Throws(Exception::class)
    fun insertReadChannel() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_2
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_2)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(resultChannels)?.blockingAwait()

        // read
        getChannelDao()?.getAllChannels()
            ?.test()
            ?.assertValue { it ->
                return@assertValue it == resultChannels
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
        getChannelDao()?.insertChannels(resultChannels)?.test()

        // read
        var insertedChannels = getChannelDao()?.getAllChannels()?.test()
        assertNotNull(insertedChannels)
        assertEquals(resultChannels, insertedChannels)
        println(insertedChannels)
        println(resultChannels)

        // delete
        getChannelDao()?.deleteAllChannels()?.blockingGet()

        // confirm the database is empty
        insertedChannels = getChannelDao()?.getAllChannels()?.test()
//        assertEquals(0, insertedChannels?.size)
        println(insertedChannels)
        println(insertedChannels)
    }

    /*
        ChannelEntity
        -   insert,
        -   read by ReadChannelByGroupId(),
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