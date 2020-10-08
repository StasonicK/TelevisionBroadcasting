package com.eburg_soft.televisionbroadcasting.presentation.presenters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveChannelsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveGroupsFromApiToDbReturnChannelIdsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveProgramsFromApiToDbUseCase
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
    private lateinit var getAllGroupsUseCase: GetAllGroupsUseCase

    @MockK
    private lateinit var getChannelsByGroupIdUseCase: GetChannelsByGroupIdUseCase

    @MockK
    private lateinit var getProgramsByChannelIdUseCase: GetProgramsByChannelIdUseCase

    @MockK
    private lateinit var saveGroupsFromApiToDbReturnChannelIdsUseCase: SaveGroupsFromApiToDbReturnChannelIdsUseCase

    @MockK
    private lateinit var saveProgramsFromApiToDbUseCase: SaveProgramsFromApiToDbUseCase

    @MockK
    private lateinit var saveChannelsFromApiToDbUseCase: SaveChannelsFromApiToDbUseCase

    @MockK
    private lateinit var removeAllGroupsUseCase: RemoveAllGroupsUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        presenter = TVMenuPresenter(
            getAllGroupsUseCase,
            getChannelsByGroupIdUseCase,
            getProgramsByChannelIdUseCase,
            saveGroupsFromApiToDbReturnChannelIdsUseCase,
            saveChannelsFromApiToDbUseCase,
            saveProgramsFromApiToDbUseCase,
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

        every { saveGroupsFromApiToDbReturnChannelIdsUseCase.execute() } returns Single.just(channelSet)
        every { saveChannelsFromApiToDbUseCase.execute(any()) } returns Single.just(channelIdList)
        every { saveProgramsFromApiToDbUseCase.execute(any(), any()) } returns Completable.complete()

        //  Act
        presenter.saveAllDataFromApiToDb()

        //  Assert
        verify(exactly = 1) { saveGroupsFromApiToDbReturnChannelIdsUseCase.execute() }
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
        every { getAllGroupsUseCase.execute() } returns Flowable.just(groupEntities)

        //  Act
        presenter.loadGroupsFromDb()

        //  Assert
        verify(exactly = 1) { getAllGroupsUseCase.execute() }
    }

    /*
        load Channels By Group Id From Db
     */
    @Test
    @Throws(Exception::class)
    fun loadChannelsByGroupIdFromDb() {
        //    Arrange
        val channelEntities = TestUtil.TEST_CHANNEL_ENTITIES_5
        every { getChannelsByGroupIdUseCase.execute(any()) } returns Flowable.just(channelEntities)

        //  Act
        presenter.loadChannelsByGroupIdFromDb("1")

        //  Assert
        verify(exactly = 1) { getChannelsByGroupIdUseCase.execute(any()) }
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
        every { getProgramsByChannelIdUseCase.execute(channelId) } returns Flowable.just(programEntities)

        //  Act
        presenter.loadProgramsByChannelIdFromDb(channelId)

        //  Assert
        verify(exactly = 1) { getProgramsByChannelIdUseCase.execute(channelId) }
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