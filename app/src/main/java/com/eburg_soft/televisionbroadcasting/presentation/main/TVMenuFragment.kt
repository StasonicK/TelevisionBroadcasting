package com.eburg_soft.televisionbroadcasting.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.core.TelevisionBroadcastingApp
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuComponent
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuContextModule
import com.eburg_soft.televisionbroadcasting.extensions.changeBackgroundColor
import com.eburg_soft.televisionbroadcasting.extensions.elevateBackOutOfTouch
import com.eburg_soft.televisionbroadcasting.extensions.elevateOnTouch
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ChannelsAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.DaysAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_tv_menu.pb_tv_menu
import kotlinx.android.synthetic.main.fragment_tv_menu.recycler_channel_list
import kotlinx.android.synthetic.main.fragment_tv_menu.recycler_days_list
import kotlinx.android.synthetic.main.fragment_tv_menu.recycler_group_list
import kotlinx.android.synthetic.main.fragment_tv_menu.recycler_programs_list
import timber.log.Timber
import javax.inject.Inject

class TVMenuFragment : Fragment(R.layout.fragment_tv_menu), TVMenuContract.View {

    @Inject
    lateinit var presenter: TVMenuContract.Presenter

    private val groupsAdapter = GroupsAdapter()
    private val channelsAdapter = ChannelsAdapter()
    private val programsAdapter = ProgramsAdapter()
    private val daysAdapter = DaysAdapter()

    private var selectedGroupId: String? = null
    private var selectedChannelId: String? = null
    private var selectedProgramId: String? = null
    private var selectedDayId: String? = null

    private var groupRecyclerTouchStatus: Boolean? = null
    private var channelRecyclerTouchStatus: Boolean? = null
    private var programRecyclerTouchStatus: Boolean? = null
    private var dayRecyclerTouchStatus: Boolean? = null

    companion object {

        const val GROUP_ID = "group id"
        const val CHANNEL_ID = "channel id"
        const val PROGRAM_ID = "program id"
        const val DAY_ID = "day id"

        const val GROUP_RECYCLER_TOUCH_STATUS = "group recycler touch status"
        const val CHANNEL_RECYCLER_TOUCH_STATUS = "channel recycler touch status"
        const val PROGRAM_RECYCLER_TOUCH_STATUS = "program recycler touch status"
        const val DAY_RECYCLER_TOUCH_STATUS = "day recycler touch status"

        @JvmStatic
        fun getNewInstance(): TVMenuFragment = TVMenuFragment()
    }

    private fun getTVMenuComponent(context: Context): TVMenuComponent =
        (context.applicationContext as TelevisionBroadcastingApp)
            .component
            .createTVMenuComponent(TVMenuContextModule(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTVMenuComponent(requireContext()).inject(this)
        presenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        savedInstanceState?.let {
            //  get ids
            selectedGroupId = it.getString(GROUP_ID)
            selectedChannelId = it.getString(CHANNEL_ID)
            selectedProgramId = it.getString(PROGRAM_ID)
            selectedDayId = it.getString(DAY_ID)

            //  get touch statuses
            groupRecyclerTouchStatus = it.getBoolean(GROUP_RECYCLER_TOUCH_STATUS)
            channelRecyclerTouchStatus = it.getBoolean(CHANNEL_RECYCLER_TOUCH_STATUS)
            programRecyclerTouchStatus = it.getBoolean(PROGRAM_RECYCLER_TOUCH_STATUS)
            dayRecyclerTouchStatus = it.getBoolean(DAY_RECYCLER_TOUCH_STATUS)
        }
        if (savedInstanceState == null) {
            presenter.syncData()
        }

        initGroupsRecycler()
        initChannelsRecycler()
        initProgramsRecycler()
        initDaysRecycler()
    }

    override fun populateGroupsRecycler() {
        if (selectedGroupId.isNullOrBlank()) presenter.getRandomGroupId()
        presenter.loadGroupsFromDb(selectedGroupId!!)
    }

    override fun populateChannelsRecycler() {
        if (selectedChannelId.isNullOrBlank()) presenter.getRandomChannelIdByGroupId(selectedGroupId!!)
        presenter.loadChannelsByGroupIdFromDb(selectedGroupId!!, selectedChannelId!!)
    }

    override fun populateProgramsRecycler() {
        if (selectedProgramId.isNullOrBlank()) presenter.getRandomProgramIdByGroupId(selectedChannelId!!)
        presenter.loadProgramsByChannelIdFromDb(selectedChannelId!!, selectedProgramId!!)
    }

    override fun populateDaysRecycler() {
        presenter.loadDaysFromDb()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //  save ids
        outState.putString(GROUP_ID, selectedGroupId)
        outState.putString(CHANNEL_ID, selectedChannelId)
        outState.putString(PROGRAM_ID, selectedProgramId)
        outState.putString(DAY_ID, selectedDayId)

        //  save touch statuses
        groupRecyclerTouchStatus?.let { outState.putBoolean(GROUP_RECYCLER_TOUCH_STATUS, it) }
        channelRecyclerTouchStatus?.let { outState.putBoolean(CHANNEL_RECYCLER_TOUCH_STATUS, it) }
        programRecyclerTouchStatus?.let { outState.putBoolean(PROGRAM_RECYCLER_TOUCH_STATUS, it) }
        dayRecyclerTouchStatus?.let { outState.putBoolean(DAY_RECYCLER_TOUCH_STATUS, it) }
    }

    override fun onStart() {
        super.onStart()
//        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.removeAllGroups()
    }

    //region ====================== Contract implementation ======================

    override fun showLoading() {
        pb_tv_menu.visibility = View.VISIBLE
        Timber.d("showLoading")
    }

    override fun hideLoading() {
        pb_tv_menu.visibility = View.GONE
        Timber.d("hideLoading")
    }

    override fun submitGroupsList(list: List<GroupEntity>) {
        groupsAdapter.submitList(list)
        Timber.d("submitGroupList")
    }

    override fun submitChannelsList(list: List<ChannelEntity>) {
        channelsAdapter.submitList(list)
        Timber.d("submitChannelList")
    }

    override fun submitProgramsList(list: List<ProgramEntity>) {
        programsAdapter.submitList(list)
        Timber.d("submitProgramList")
    }

    override fun submitDaysList(list: List<DayEntity>) {
        daysAdapter.submitList(list)
        Timber.d("submitDaysList")
    }

    override fun saveSelectedGroupId(groupId: String) {
        selectedGroupId = groupId
        Timber.d("submitDefaultGroupId: $groupId")
        showNetworkErrorMessage("groupId: $selectedGroupId")
    }

    override fun saveSelectedChannelId(channelId: String) {
        selectedChannelId = channelId
        Timber.d("submitDefaultChannelId: $channelId")
        showNetworkErrorMessage("channelId: $selectedChannelId")
    }

    override fun saveSelectedProgramId(channelId: String) {
        selectedChannelId = channelId
        Timber.d("submitDefaultChannelId: $channelId")
        showNetworkErrorMessage("channelId: $selectedChannelId")
    }

    override fun saveSelectedDayId(dayId: String) {
        selectedDayId = dayId
        Timber.d("submitDefaultChannelId: $dayId")
        showNetworkErrorMessage("channelId: $selectedDayId")
    }

    override fun showNetworkErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
        Timber.d(message)
    }

    override fun initGroupsRecycler() {
        recycler_group_list.apply {
            groupsAdapter.apply {
                setOnClick { any, view ->
                    (any as GroupEntity).let {
                        presenter.loadChannelsByGroupIdFromDb(selectedGroupId!!, it.id)
                        selectedGroupId = it.id
                    }

                    view.changeBackgroundColor(R.color.blue)
                }
                setOnTouch {
                    groupRecyclerTouchStatus = it
                }
            }

            // TODO: 08.10.2020 create listener
            groupRecyclerTouchStatus?.let {
                if (it) {
                    changeBackgroundColor(R.color.white_transparent)
                    elevateOnTouch()
                } else {
                    changeBackgroundColor(R.color.black)
                    elevateBackOutOfTouch()
                }
            }

            adapter = groupsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        Timber.d("showGroupsRecycler accomplished")
    }

    override fun initChannelsRecycler() {
        recycler_channel_list.apply {
            channelsAdapter.setOnClick { any, view ->
                (any as ChannelEntity).let {
                    presenter.loadProgramsByChannelIdFromDb(selectedChannelId!!, it.id)
                    selectedChannelId = it.id
                }
                view.isFocusable = true
            }

            channelsAdapter.setOnTouch {

            }
            // TODO: 06.10.2020 add item highlighting

            adapter = channelsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("showChannelsRecycler accomplished")
    }

    override fun initProgramsRecycler() {
        recycler_programs_list.apply {
            programsAdapter.setOnClick { any, view ->
                (any as ProgramEntity).let {
                    selectedProgramId = any.id
                }
                view.isFocusable = true
            }
            adapter = programsAdapter
            // TODO: 06.10.2020 add item highlighting

            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("showProgramsRecycler accomplished")
    }

    override fun initDaysRecycler() {
        recycler_days_list.apply {
            daysAdapter.setOnClick { any, view ->
                (any as DayEntity).let {
                    selectedDayId = it.id
                }
            }
            adapter = daysAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("showDaysRecycler accomplished")
    }

    //endregion
}