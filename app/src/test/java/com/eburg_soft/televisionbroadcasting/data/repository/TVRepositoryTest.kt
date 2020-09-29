package com.eburg_soft.televisionbroadcasting.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eburg_soft.televisionbroadcasting.core.datatype.Result
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.TVApi
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TVNetworkDataSourceImpl
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.ProgramMapper
import eburg_soft.televisionbroadcasting.utils.TestUtil
import io.mockk.*
import io.mockk.junit5.*
import org.junit.*
import org.junit.jupiter.api.extension.*
import org.junit.rules.*

@ExtendWith(MockKExtension::class)
class TVRepositoryTest {

    // TODO: 29.09.2020 fix methods! test class is not correct! 
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: TVRepository

    //    @MockK
    private lateinit var groupDao: GroupDao

    //    @MockK
    private lateinit var channelDao: ChannelDao

    //    @MockK
    private lateinit var programDao: ProgramDao

    //    @MockK
    private lateinit var tvApi: TVApi

    //  verify on interface
    private lateinit var mTvNetworkDataSource: TVNetworkDataSource

    //    @MockK
    private lateinit var programMapper: ProgramMapper

    @Before
    fun init() {
//        MockKAnnotations.init(this, relaxUnitFun = true)
        groupDao = spyk()
        channelDao = spyk()
        programDao = spyk()
        tvApi = spyk()
        mTvNetworkDataSource = TVNetworkDataSourceImpl(tvApi)
        programMapper = spyk()
        mockkObject(GroupMapper)
        repository =
            TVRepositoryImpl(groupDao, channelDao, programDao, mTvNetworkDataSource, programMapper)
    }

    @After
    fun finish() {
        unmockkAll()
    }

    /*
        save group entities from API to database
     */
    @Test
    @Throws(Exception::class)
    fun saveGroupsAndChannelsFromApiToDb() {
        //  Arrange
        val result = Result.success(TestUtil.TEST_GROUP_RESPONSES)
        val map = GroupMapper.map(result.data)
        val groups = map.keys.toList().sortedBy { it.id }
        val channels = mutableListOf<ChannelEntity>()
        map.values.forEach { list1 -> channels.addAll(list1.sortedBy { it.id }) }
//        every { tvNetworkDataSource.getGroupsAndChannelsFromApi() } returns Single.just(result)
//        every { GroupMapper.map(any()) } returns map
//        every { groupDao.insertGroups(groups) } returns Completable.complete()
//        every { channelDao.insertChannels(channels) } returns Completable.complete()

        //  Act
//        repository.saveGroupsAndChannelsFromApiToDb()
//
//        val result1 = repository.getAllGroups().blockingFirst()
//        println(result1)
//        groupDao.insertGroups(groups).blockingAwait()
//        channelDao.insertChannels(channels).blockingAwait()

        //  Assert
//        verify(exactly = 1) { GroupMapper.map(result.data) }
//        verify(exactly = 1) { groupDao.insertGroups(groups) }
//        verify(exactly = 1) { channelDao.insertChannels(channels) }
    }

    /*
        save program entities from API to database
     */
    @Test
    @Throws(Exception::class)
    fun saveProgramsFromApiToDb() {
        //  Arrange
        val id = 1
        val count = 10
        val channelId = 1
        val programResponses = Result.success(TestUtil.generateTestProgramResponses(1, count))
        val programEntities = TestUtil.generateTestProgramEntities(id, count, channelId)

//        every { mTvNetworkDataSource.getProgramsFromApi(any()) } returns Single.just(programResponses)
//        every { programMapper.setChannelId(channelId) } just Runs
//        every { programMapper.map(programResponses.data) } returns programEntities
//        every { programDao.insertPrograms(programEntities) } returns Completable.complete()

        //  Act
//        repository.saveProgramsFromApiToDb(id.toString(), channelId).blockingAwait()

        //  Assert
//        verify(exactly = 1) { mTvNetworkDataSource.getProgramsFromApi(id.toString()) }
//        verify(exactly = 1) { programMapper.setChannelId(channelId) }
//        verify(exactly = 1) { programMapper.map(programResponses.data) }
//        verify(exactly = 1) { programDao.insertPrograms(programEntities) }
    }
}