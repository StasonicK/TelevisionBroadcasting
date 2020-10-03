package com.eburg_soft.televisionbroadcasting.presentation.presenters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.mappers.GroupMapper
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveGroupsAndChannelsFromApiToDbReturnIdsUseCase
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
    private lateinit var saveGroupsAndChannelsFromApiToDbReturnIdsUseCase: SaveGroupsAndChannelsFromApiToDbReturnIdsUseCase

    @MockK
    private lateinit var saveProgramsFromApiToDbUseCase: SaveProgramsFromApiToDbUseCase

    @MockK
    private lateinit var removeAllGroupsUseCase: RemoveAllGroupsUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        presenter = TVMenuPresenter(
            getAllGroupsUseCase,
            getChannelsByGroupIdUseCase,
            getProgramsByChannelIdUseCase,
            saveGroupsAndChannelsFromApiToDbReturnIdsUseCase,
            saveProgramsFromApiToDbUseCase,
            removeAllGroupsUseCase
        )
    }

    @After
    fun finish() {
        unmockkAll()
    }

    /*
        sync data
     */
    @Test
    @Throws(Exception::class)
    fun syncData() {
        //    Arrange
        val groupResponses = TestUtil.TEST_GROUP_RESPONSES
        val map = GroupMapper.map(groupResponses)
        val groupEntities = map.keys.toList().sortedBy { it.id }
        val channels = mutableListOf<ChannelEntity>()
        map.values.forEach { list1 -> channels.addAll(list1.sortedBy { it.id }) }
        val ids: ArrayList<String> = ArrayList()
        channels.forEach { channelEntity ->
            ids.add(channelEntity.id.substringAfterLast("-"))
        }
        every { getAllGroupsUseCase.execute() } returns Flowable.just(groupEntities)
        every { saveGroupsAndChannelsFromApiToDbReturnIdsUseCase.execute() } returns Single.just(ids)
        every { saveProgramsFromApiToDbUseCase.execute(any(), any()) } returns Completable.complete()

        //  Act
        presenter.syncData()

        //  Assert
        verify(exactly = 1) { getAllGroupsUseCase.execute() }
        verify(exactly = 1) { saveGroupsAndChannelsFromApiToDbReturnIdsUseCase.execute() }
        verify(exactly = 25) { saveProgramsFromApiToDbUseCase.execute(any(), any()) }
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