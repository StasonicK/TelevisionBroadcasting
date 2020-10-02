package com.eburg_soft.televisionbroadcasting.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.core.TelevisionBroadcastingApp
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuComponent
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuContextModule
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ChannelsRecyclerAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsRecyclerAdapter
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ProgramsRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.pb_main
import kotlinx.android.synthetic.main.fragment_tv_menu1.recycler_channel_list
import kotlinx.android.synthetic.main.fragment_tv_menu1.recycler_group_list
import kotlinx.android.synthetic.main.fragment_tv_menu1.recycler_programs_list
import timber.log.Timber
import javax.inject.Inject

class TVMenuFragment : Fragment(), TVMenuContract.View {

    @Inject
    lateinit var presenter: TVMenuContract.Presenter

    //    private val groupAdapter: GroupsAdapter? = null
//    private val channelsAdapter: ChannelsAdapter? = null
//    private val programsAdapter: ProgramsAdapter? = null
    private val groupAdapter: GroupsRecyclerAdapter? = null
    private val channelsAdapter: ChannelsRecyclerAdapter? = null
    private val programsAdapter: ProgramsRecyclerAdapter? = null
//    private val dayAdapter: DaysAdapter? = null

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
        retainInstance = true

        savedInstanceState?.let {
            groupId = it.getString(GROUP_ID)
            channelId = it.getString(CHANNEL_ID)
            programId = it.getString(PROGRAM_ID)
            dayId = it.getString(DAY_ID)
        }
        getTVMenuComponent(requireContext()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_menu1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showGroupsRecycler(groupId)
        showChannelsRecycler(channelId)
        showProgramsRecycler(programId)
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

    override fun showNetworkErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
        Timber.d(message)
    }

//    fun pupulate

    override fun showGroupsRecycler(grId: String?) {
        recycler_group_list.apply {
            groupAdapter?.setOnClick { any, view ->
                (any as GroupEntity).let {
                    presenter.loadChannelsByGroupIdFromDb(it.id)
                    groupId = it.id
                }
                view?.isFocusable = true
            }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            presenter.loadGroupsFromDb()
            adapter = groupAdapter
        }
        Timber.d("showGroupsList accomplished")
    }

    override fun showChannelsRecycler(chId: String?) {
        recycler_channel_list.apply {
            channelsAdapter?.setOnClick { any, view ->
                (any as GroupEntity).let {
                    presenter.loadProgramsByChannelIdFromDb(any.id)
                    channelId = any.id
                }
                view?.isFocusable = true
            }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            val groups = groupAdapter?.currentList
//            val sizeGroupList = groupAdapter?.currentList?.size
//            val halfSize = (sizeGroupList?.div(2))
//            val centerGroup = halfSize?.let { groups?.get(it) }
//            centerGroup?.id?.let { presenter.loadChannelsByGroupIdFromDb(it) }
            adapter = channelsAdapter
        }
        Timber.d("showChannelsList accomplished")
    }

    override fun showProgramsRecycler(prId: String?) {
        recycler_programs_list.apply {
            programsAdapter?.setOnClick { any, view ->
                (any as ProgramEntity).let {
                    presenter.loadProgramsByChannelIdFromDb(any.id)
                    programId = any.id
                }
                view?.isFocusable = true
            }
        }
        Timber.d("showProgramsList accomplished")
    }

    //endregion
}