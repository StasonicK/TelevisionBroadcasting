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
        val resultChannels = arrayListOf(TestUtil.TEST_CHANNEL_ENTITY_1_1)
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_1)

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
        -   insert group, channels,
        -   read channels,
        -   delete all channels
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteChannels() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_2
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_2)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(resultChannels)?.blockingAwait()

        // read
        var insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
        assertNotNull(insertedChannels)
        assertEquals(resultChannels, insertedChannels)
        println(insertedChannels)
        println(resultChannels)

        // delete
        getChannelDao()?.deleteAllChannels()?.blockingGet()

        // confirm the Channels table is empty
        insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
        assertEquals(0, insertedChannels?.size)
        println(insertedChannels)
    }

    /*
        ChannelEntity
        -   insert group, channels,
        -   read channels,
        -   delete all groups
     */
    @Test
    @Throws(Exception::class)
    fun insertReadDeleteChannelsByDeletingGroups() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_2
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_2)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(resultChannels)?.blockingAwait()

        // read
        var insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
        assertNotNull(insertedChannels)
        assertEquals(resultChannels, insertedChannels)
        println(insertedChannels)
        println(resultChannels)

        // delete
        getGroupDao()?.deleteAllGroups()?.blockingAwait()

        // confirm the database is empty
        insertedChannels = getChannelDao()?.getAllChannels()?.blockingFirst()
        assertEquals(0, insertedChannels?.size)
        println(insertedChannels)
        val insertedGroups = getGroupDao()?.getAllGroups()?.blockingFirst()
        assertEquals(0, insertedGroups?.size)
        println(insertedGroups)
    }

    /*
        ChannelEntity
        -   insert group, channels,
        -   read by ReadChannelByGroupId(),
        -   delete all channels
     */
    @Test
    @Throws(Exception::class)
    fun insertReadChannelByGroupIdDeleteChannels() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_3
        val groupIds: ArrayList<String> = ArrayList()
        resultChannels.forEach {
            groupIds.add(it.groupId)
        }
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_3)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(resultChannels)?.blockingGet()

        // read
        getChannelDao()?.getChannelByGroupId(groupIds[0])
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == resultChannels
            }

        // delete
        getChannelDao()?.deleteAllChannels()?.blockingGet()

        // confirm the Channels table is empty
        getChannelDao()?.getAllChannels()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
    }

    /*
        ChannelEntity
        -   insert group, channels,
        -   read by ReadChannelByGroupId(),
        -   delete all groups
     */
    @Test
    @Throws(Exception::class)
    fun insertReadChannelByGroupIdDeleteChannelsByDeletingGroups() {
        val resultChannels = TestUtil.TEST_CHANNEL_ENTITIES_3
        val groupIds: ArrayList<String> = ArrayList()
        resultChannels.forEach {
            groupIds.add(it.groupId)
        }
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_3)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(resultChannels)?.blockingGet()

        // read
        getChannelDao()?.getChannelByGroupId(groupIds[0])
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == resultChannels
            }

        // delete
        getGroupDao()?.deleteAllGroups()?.blockingAwait()

        // confirm the database is empty
        getChannelDao()?.getAllChannels()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
        getGroupDao()?.getAllGroups()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
    }
}