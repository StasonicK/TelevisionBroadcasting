package com.eburg_soft.televisionbroadcasting.presentation.presenters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchChannelsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchDaysFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchGroupsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchProgramsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllDaysFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuPresenter
import eburg_soft.televisionbroadcasting.utils.TestUtil
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.*
import org.junit.jupiter.api.extension.*

@ExtendWith(MockKExtension::class)
class TVMenuPresenterTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var presenter: TVMenuPresenter

    @MockK
    private lateinit var mGetAllGroupsFromDbUseCase: GetAllGroupsFromDbUseCase

    @MockK
    private lateinit var mGetChannelsByGroupIdFromDbUseCase: GetChannelsByGroupIdFromDbUseCase

    @MockK
    private lateinit var mGetProgramsByChannelIdFromDbUseCase: GetProgramsByChannelIdFromDbUseCase

    @MockK
    private lateinit var getAllDaysFromDbUseCase: GetAllDaysFromDbUseCase

    @MockK
    private lateinit var mFetchGroupsFromApiIntoDbUseCase: FetchGroupsFromApiIntoDbUseCase

    @MockK
    private lateinit var mFetchProgramsFromApiIntoDbUseCase: FetchProgramsFromApiIntoDbUseCase

    @MockK
    private lateinit var mFetchChannelsFromApiIntoDbUseCase: FetchChannelsFromApiIntoDbUseCase

    @MockK
    private lateinit var mFetchDaysFromApiIntoDbUseCase: FetchDaysFromApiIntoDbUseCase

    @MockK
    private lateinit var removeAllGroupsUseCase: RemoveAllGroupsUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        presenter = TVMenuPresenter(
            mGetAllGroupsFromDbUseCase,
            mGetChannelsByGroupIdFromDbUseCase,
            mGetProgramsByChannelIdFromDbUseCase,
            getAllDaysFromDbUseCase,
            mFetchGroupsFromApiIntoDbUseCase,
            mFetchChannelsFromApiIntoDbUseCase,
            mFetchProgramsFromApiIntoDbUseCase,
            mFetchDaysFromApiIntoDbUseCase,
            removeAllGroupsUseCase
        )
    }

    @After
    fun finish() {
        unmockkAll()
    }

    /*
        save All Data From Api To Db
     */
    @Test
    @Throws(Exception::class)
    fun saveAllDataFromApiToDb() {
        //    Arrange
        val groupResponses = TestUtil.TEST_GROUP_RESPONSES
        val map = GroupMapper.map(groupResponses)
        val groupEntities = map.keys.toList().sortedBy { it.id }

        val channelMutableSet = mutableSetOf<ChannelEntity>()
        map.values.forEach {
            channelMutableSet.addAll(it)
        }

        val channelSet: Set<ChannelEntity> = HashSet(channelMutableSet)
        val channelIdMutableList = mutableListOf<String>()
        channelMutableSet.forEach {
            channelIdMutableList.add(it.id)
        }
        channelIdMutableList.sortWith { p0, p1 -> // pattern: "channel-id-x-x"
            val cuttingPart = "channel-id-"
            val p01 = p0!!.substringAfter(cuttingPart)
            val p11 = p1!!.substringAfter(cuttingPart)
            val digits0 = p01.split("-")
            val digits1 = p11.split("-")
            when {
                digits0[0].toInt() > digits1[0].toInt() -> 1
                digits0[0].toInt() == digits1[0].toInt() -> when {
                    digits0[1].toInt() > digits1[1].toInt() -> 1
                    digits0[1].toInt() == digits1[1].toInt() -> 1
                    else -> -1
                }
                else -> -1
            }
        }
        val channelIdList: List<String> = ArrayList<String>(channelIdMutableList)

        every { mFetchGroupsFromApiIntoDbUseCase.execute() } returns Single.just(channelSet)
        every { mFetchChannelsFromApiIntoDbUseCase.execute(any()) } returns Single.just(channelIdList)
        every { mFetchProgramsFromApiIntoDbUseCase.execute(any(), any()) } returns Completable.complete()

        //  Act
        presenter.fetchAllDataFromApiToDb()

        //  Assert
        verify(exactly = 1) { mFetchGroupsFromApiIntoDbUseCase.execute() }
//        verify(exactly = 1) { saveChannelsFromApiToDbUseCase.execute(any()) }
//        verify(exactly = 25) { saveProgramsFromApiToDbUseCase.execute(any(), any()) }
    }

    /*
        load Groups From Db
     */
    @Test
    @Throws(Exception::class)
    fun loadGroupsFromDb() {
        //    Arrange
        val groupResponses = TestUtil.TEST_GROUP_RESPONSES
        val map = GroupMapper.map(groupResponses)
        val groupEntities = map.keys.toList().sortedBy { it.id }
        every { mGetAllGroupsFromDbUseCase.execute() } returns Flowable.just(groupEntities)

        //  Act
        presenter.loadGroupsFromDb()

        //  Assert
        verify(exactly = 1) { mGetAllGroupsFromDbUseCase.execute() }
    }

    /*
        load Channels By Group Id From Db
     */
    @Test
    @Throws(Exception::class)
    fun loadChannelsByGroupIdFromDb() {
        //    Arrange
        val channelEntities = TestUtil.TEST_CHANNEL_ENTITIES_5
        every { mGetChannelsByGroupIdFromDbUseCase.execute(any()) } returns Flowable.just(channelEntities)

        //  Act
        presenter.loadChannelsByGroupIdFromDb("1")

        //  Assert
        verify(exactly = 1) { mGetChannelsByGroupIdFromDbUseCase.execute(any()) }
    }

    /*
        loadProgramsByChannelIdFromDb
     */
    @Test
    @Throws(Exception::class)
    fun loadProgramsByChannelIdFromDb() {
        //    Arrange
        val channelId = "1"
        val programEntities = TestUtil.generateTestProgramEntities(1, 1, channelId)
        every { mGetProgramsByChannelIdFromDbUseCase.execute(channelId) } returns Flowable.just(programEntities)

        //  Act
        presenter.loadProgramsByChannelIdFromDb(channelId)

        //  Assert
        verify(exactly = 1) { mGetProgramsByChannelIdFromDbUseCase.execute(channelId) }
    }

    /*
        remove All Groups
     */
    @Test
    @Throws(Exception::class)
    fun removeAllGroups() {
        //    Arrange
        every { removeAllGroupsUseCase.execute() } returns Completable.complete()

        //  Act
        presenter.removeAllGroups()

        //  Assert
        verify(exactly = 1) { removeAllGroupsUseCase.execute() }
    }
}