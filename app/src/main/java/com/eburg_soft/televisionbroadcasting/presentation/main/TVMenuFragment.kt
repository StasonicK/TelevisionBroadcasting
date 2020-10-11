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

    private var groupRecyclerTouchStatus: Boolean = false
    private var channelRecyclerTouchStatus: Boolean = false
    private var programRecyclerTouchStatus: Boolean = false
    private var dayRecyclerTouchStatus: Boolean = false

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

//        initGroupsRecycler()
//        initChannelsRecycler()
//        initProgramsRecycler()
//        initDaysRecycler()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //  save ids
        outState.putString(GROUP_ID, selectedGroupId)
        outState.putString(CHANNEL_ID, selectedProgramId)
        outState.putString(PROGRAM_ID, selectedProgramId)
        outState.putString(DAY_ID, selectedDayId)

        //  save touch statuses
        outState.putBoolean(GROUP_RECYCLER_TOUCH_STATUS, groupRecyclerTouchStatus)
        outState.putBoolean(CHANNEL_RECYCLER_TOUCH_STATUS, channelRecyclerTouchStatus)
        outState.putBoolean(PROGRAM_RECYCLER_TOUCH_STATUS, programRecyclerTouchStatus)
        outState.putBoolean(DAY_RECYCLER_TOUCH_STATUS, dayRecyclerTouchStatus)
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
        this.selectedChannelId = channelId
        Timber.d("submitDefaultChannelId: $channelId")
        showNetworkErrorMessage("channelId: ${this.selectedChannelId}")
    }

    override fun saveSelectedProgramId(programId: String) {
        this.selectedProgramId = programId
        Timber.d("submitDefaultChannelId: $programId")
        showNetworkErrorMessage("channelId: ${this.selectedProgramId}")
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
                        presenter.loadChannelsByGroupIdFromDb(it.id)
                        selectedGroupId = it.id
//                        showNetworkErrorMessage("Clicked on $selectedGroupId")
                    }
                    view.changeBackgroundColor(R.color.blue)
                }
                setOnTouch {
                    groupRecyclerTouchStatus = it
                    Timber.d("Group $it touched")
                }
            }

            // TODO: 08.10.2020 create listener
            groupRecyclerTouchStatus.apply {
                if (groupRecyclerTouchStatus) {
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

        Timber.d("initGroupsRecycler accomplished")
    }

    override fun initChannelsRecycler() {
        recycler_channel_list.apply {
            channelsAdapter.apply {
                setOnClick { any, view ->
                    (any as ChannelEntity).let {
                        presenter.loadProgramsByChannelIdFromDb(it.id)
                        selectedChannelId = it.id
                        showNetworkErrorMessage("Clicked on $selectedChannelId")
                    }
                    setOnTouch {
                        channelRecyclerTouchStatus = it
                        Timber.d("Channel $it touched")
                    }
                }
            }
            // TODO: 08.10.2020 create listener
            channelRecyclerTouchStatus.apply {
                if (channelRecyclerTouchStatus) {
                    changeBackgroundColor(R.color.white_transparent)
                    elevateOnTouch()
                } else {
                    changeBackgroundColor(R.color.black)
                    elevateBackOutOfTouch()
                }
            }

            // TODO: 06.10.2020 add item highlighting

            adapter = channelsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("initChannelsRecycler accomplished")
    }

    override fun initProgramsRecycler() {
        recycler_programs_list.apply {
            programsAdapter.apply {
                setOnClick { any, view ->
                    (any as ProgramEntity).let {
                        selectedProgramId = it.id
                        showNetworkErrorMessage("Clicked on $selectedProgramId")
                    }
                    view.isFocusable = true
                }
                setOnTouch {
                    programRecyclerTouchStatus = it
                    Timber.d("Program $it touched")
                }
            }

            // TODO: 08.10.2020 create listener
            programRecyclerTouchStatus.apply {
                if (channelRecyclerTouchStatus) {
                    changeBackgroundColor(R.color.white_transparent)
                    elevateOnTouch()
                } else {
                    changeBackgroundColor(R.color.black)
                    elevateBackOutOfTouch()
                }
            }
            adapter = programsAdapter
            // TODO: 06.10.2020 add item highlighting

            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("initProgramsRecycler accomplished")
    }

    override fun initDaysRecycler() {
        recycler_days_list.apply {
            daysAdapter.apply {
                setOnClick { any, view ->
                    (any as DayEntity).let {
                        selectedDayId = it.id
                        showNetworkErrorMessage("Clicked on $selectedDayId")
                    }
                }
                setOnTouch {
                    dayRecyclerTouchStatus = it
                    Timber.d("Day $it touched")
                }
            }

            // TODO: 08.10.2020 create listener
            dayRecyclerTouchStatus.apply {
                if (dayRecyclerTouchStatus) {
                    changeBackgroundColor(R.color.white_transparent)
                    elevateOnTouch()
                } else {
                    changeBackgroundColor(R.color.black)
                    elevateBackOutOfTouch()
                }
            }
            adapter = daysAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("initDaysRecycler accomplished")
    }

    override fun populateGroupsRecycler() {
        presenter.verifySelectedIdAndLoadGroupsFromDb(selectedGroupId)
        Timber.d("populateGroupsRecycler accomplished")
    }

    override fun populateChannelsRecycler() {
        presenter.verifySelectedIdAndLoadChannelsFromDb(selectedGroupId!!, selectedChannelId)
        Timber.d("populateChannelsRecycler accomplished")
    }

    override fun populateProgramsRecycler() {
        presenter.verifySelectedIdAndLoadProgramsFromDb(selectedChannelId!!, selectedProgramId)
        Timber.d("populateProgramsRecycler accomplished")
    }

    override fun populateDaysRecycler() {
        presenter.verifySelectedIdAndLoadDaysFromDb(selectedDayId)
        Timber.d("populateDaysRecycler accomplished")
    }

    //endregion
}