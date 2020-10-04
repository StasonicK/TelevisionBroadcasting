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

    private val groupAdapter: GroupsRecyclerAdapter? = null
    private val channelsAdapter: ChannelsRecyclerAdapter? = null
    private val programsAdapter: ProgramsRecyclerAdapter? = null
    private val dayAdapter: DaysRecyclerAdapter? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.syncData()
        showGroupsRecycler()
        showChannelsRecycler(groupId)
        showProgramsRecycler(channelId)
        showDaysRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            groupId = it.getString(GROUP_ID)
            channelId = it.getString(CHANNEL_ID)
            programId = it.getString(PROGRAM_ID)
            dayId = it.getString(DAY_ID)
        }
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

    override fun submitGroupsList(list: List<GroupEntity>) {
        groupAdapter?.setData(list)
    }

    override fun submitChannelsList(list: List<ChannelEntity>) {
        channelsAdapter?.setData(list)
    }

    override fun submitProgramsList(list: List<ProgramEntity>) {
        programsAdapter?.setData(list)
    }

    override fun submitDaysList(list: List<DayEntity>) {
        dayAdapter?.setData(list)
    }

    override fun showNetworkErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
        Timber.d(message)
    }

    override fun showGroupsRecycler() {
        recycler_group_list.apply {
            groupAdapter?.setOnClick { any, view ->
                (any as GroupEntity).let {
                    presenter.loadChannelsByGroupIdFromDb(it.id)
                    groupId = it.id
                }
                view?.isFocusable = true
            }
            presenter.loadGroupsFromDb()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            setHasFixedSize(true)
        }
        Timber.d("showGroupsList accomplished")
    }

    override fun showChannelsRecycler(grId: String?) {
        recycler_channel_list.apply {
            channelsAdapter?.setOnClick { any, view ->
                (any as GroupEntity).let {
                    presenter.loadProgramsByChannelIdFromDb(any.id)
                    channelId = any.id
                }
                view?.isFocusable = true
            }
            grId?.let { presenter.loadChannelsByGroupIdFromDb(grId) }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = channelsAdapter
            setHasFixedSize(true)
        }
        Timber.d("showChannelsList accomplished")
    }

    override fun showProgramsRecycler(chId: String?) {
        recycler_programs_list.apply {
            programsAdapter?.setOnClick { any, view ->
                (any as ProgramEntity).let {
                    programId = any.id
                }
                view?.isFocusable = true
            }
            chId?.let { presenter.loadProgramsByChannelIdFromDb(it) }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = programsAdapter
            setHasFixedSize(true)
        }
        Timber.d("showProgramsList accomplished")
    }

    override fun showDaysRecycler() {
        recycler_days_list.apply {
            presenter.loadDaysByChannelIdFromDb()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dayAdapter
            setHasFixedSize(true)
        }
    }

    //endregion
}