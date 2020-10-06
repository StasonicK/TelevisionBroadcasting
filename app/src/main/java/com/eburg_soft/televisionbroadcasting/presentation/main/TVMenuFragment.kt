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
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ChannelsAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.DaysAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.pb_main
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

    private var groupId: String? = null
    private var channelId: String? = null
    private var programId: String? = null
    private var dayId: String? = null

    companion object {

        const val GROUP_ID = "group id"
        const val CHANNEL_ID = "channel id"
        const val PROGRAM_ID = "program id"
        const val DAY_ID = "day id"

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
            groupId = it.getString(GROUP_ID)
            channelId = it.getString(CHANNEL_ID)
            programId = it.getString(PROGRAM_ID)
            dayId = it.getString(DAY_ID)
        }

        showGroupsRecycler()
        showChannelsRecycler()
        showProgramsRecycler()
        showDaysRecycler()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(GROUP_ID, groupId)
        outState.putString(CHANNEL_ID, channelId)
        outState.putString(PROGRAM_ID, programId)
        outState.putString(DAY_ID, dayId)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    //region ====================== Contract implementation ======================

    override fun showLoading() {
        requireActivity().pb_main.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        requireActivity().pb_main.visibility = View.GONE
    }

    override fun submitGroupList(list: List<GroupEntity>) {
        groupsAdapter.submitList(list)
    }

    override fun submitChannelList(list: List<ChannelEntity>) {
        channelsAdapter.submitList(list)
    }

    override fun submitProgramList(list: List<ProgramEntity>) {
        programsAdapter.submitList(list)
    }

    override fun submitDaysList(list: List<DayEntity>) {
        daysAdapter.submitList(list)
    }

    override fun submitDefaultGroupId(groupId: String) {
//        if (this.groupId.isNullOrEmpty())
            this.groupId = groupId
    }

    override fun submitDefaultChannelId(channelId: String) {
//        if (this.channelId.isNullOrEmpty())
            this.channelId = channelId
    }

    override fun showNetworkErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
        Timber.d(message)
    }

    override fun showGroupsRecycler() {
        recycler_group_list.apply {
            groupsAdapter.setOnClick { any, view ->
                (any as GroupEntity).let {
                    presenter.loadChannelsByGroupIdFromDb(it.id)
                    groupId = it.id
                }
                view.isFocusable = true
            }
            presenter.loadGroupsFromDb()
            adapter = groupsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("showGroupsRecycler accomplished")
    }

    override fun showChannelsRecycler() {
        recycler_channel_list.apply {
            channelsAdapter.setOnClick { any, view ->
                (any as GroupEntity).let {
                    presenter.loadProgramsByChannelIdFromDb(any.id)
                    channelId = any.id
                }
                view.isFocusable = true
            }
            adapter = channelsAdapter
            // TODO: 06.10.2020 add item highlighting

            channelId?.let { presenter.loadChannelsByGroupIdFromDb(it) }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("showChannelsRecycler accomplished")
    }

    override fun showProgramsRecycler() {
        recycler_programs_list.apply {
            programsAdapter.setOnClick { any, view ->
                (any as ProgramEntity).let {
                    programId = any.id
                }
                view.isFocusable = true
            }
            adapter = programsAdapter
            // TODO: 06.10.2020 add item highlighting

            programId?.let { presenter.loadProgramsByChannelIdFromDb(it) }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        Timber.d("showProgramsRecycler accomplished")
    }

    override fun showDaysRecycler() {
        recycler_days_list.apply {
            daysAdapter.setOnClick { any, view ->
                (any as DayEntity).let {
                    dayId = it.id
                }
            }
            presenter.loadDaysFromDb()
            adapter = daysAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    //endregion
}