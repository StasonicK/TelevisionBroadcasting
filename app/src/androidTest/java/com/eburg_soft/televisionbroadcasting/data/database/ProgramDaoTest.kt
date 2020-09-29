package com.eburg_soft.televisionbroadcasting.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.*

class ProgramDaoTest : TVDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    /*
        ProgramEntity
        -   insert,
        -   read ProgramByChannelId()
     */
    @Test
    @Throws(Exception::class)
    fun insertReadProgramByChannelId() {
        val id = 1
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_1)
        val channels = arrayListOf(TestUtil.TEST_CHANNEL_ENTITY_1_1)
        val channelId = channels[0].id
        val expectedPrograms = TestUtil.generateTestProgramEntities(id, 1, channelId)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(channels)?.blockingAwait()
        getProgramDao()?.insertPrograms(expectedPrograms)?.blockingAwait()

        // read
        getProgramDao()?.getProgramsByChannelId(channelId)
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == expectedPrograms
            }
    }

    /*
        ProgramEntity
        -   insert,
        -   read ProgramById()
     */
    @Test
    @Throws(Exception::class)
    fun insertReadProgramById() {
        val idNumber = 1
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_1)
        val channels = arrayListOf(TestUtil.TEST_CHANNEL_ENTITY_1_1)
        val channelId = channels[0].id
        val expectedPrograms = TestUtil.generateTestProgramEntities(idNumber, 1, channelId)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(channels)?.blockingAwait()
        getProgramDao()?.insertPrograms(expectedPrograms)?.blockingAwait()

        // read
        val id = expectedPrograms[0].id

        getProgramDao()?.getProgramById(id)
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == expectedPrograms
            }
    }

    /*
        ProgramEntity
        -   insert,
        -   read,
        -   delete channels
     */
    @Test
    @Throws(Exception::class)
    fun insertReadProgramByChannelIdDeleteProgramsByDeletingChannels() {
        val id = 1
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_1)
        val channels = arrayListOf(TestUtil.TEST_CHANNEL_ENTITY_1_1)
        val channelId = channels[0].id
        val expectedPrograms = TestUtil.generateTestProgramEntities(id, 1, channelId)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(channels)?.blockingAwait()
        getProgramDao()?.insertPrograms(expectedPrograms)?.blockingAwait()

        // read
        getProgramDao()?.getProgramsByChannelId(channelId)
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == expectedPrograms
            }

        //  delete channels
        getChannelDao()?.deleteAllChannels()?.blockingAwait()

        //  confirm Programs table is empty
        getProgramDao()?.getAllPrograms()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
        getChannelDao()?.getAllChannels()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
    }

    /*
        ProgramEntity
        -   insert,
        -   read,
        -   delete groups
     */
    @Test
    @Throws(Exception::class)
    fun insertReadProgramByChannelIdDeleteProgramsByDeletingGroups() {
        val id = 1
        val group = arrayListOf(TestUtil.TEST_GROUP_ENTITY_1)
        val channels = arrayListOf(TestUtil.TEST_CHANNEL_ENTITY_1_1)
        val channelId = channels[0].id
        val expectedPrograms = TestUtil.generateTestProgramEntities(id, 1, channelId)

        // insert
        getGroupDao()?.insertGroups(group)?.blockingAwait()
        getChannelDao()?.insertChannels(channels)?.blockingAwait()
        getProgramDao()?.insertPrograms(expectedPrograms)?.blockingAwait()

        // read
        getProgramDao()?.getProgramsByChannelId(channelId)
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it == expectedPrograms
            }

        //  delete channels
        getGroupDao()?.deleteAllGroups()?.blockingAwait()

        // confirm the database is empty
        getProgramDao()?.getAllPrograms()
            ?.test()
            ?.assertNoErrors()
            ?.assertValue { it ->
                return@assertValue it.isEmpty()
            }
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