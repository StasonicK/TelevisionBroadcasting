package com.eburg_soft.televisionbroadcasting.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.GroupsAdapter
import javax.inject.Inject

class TVMenuFragment : Fragment(), TVMenuContract.View {

    @Inject
    private lateinit var presenter: TVMenuPresenter

    private val groupAdapter: GroupsAdapter? = null

    companion object {

        @JvmStatic
        fun getNewInstance(): TVMenuFragment = TVMenuFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    //region ====================== Contract implementation ======================

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun submitGroupsList(list: List<GroupEntity>) {
        TODO("Not yet implemented")
    }

    override fun submitChannelsList(list: List<ChannelEntity>) {
        TODO("Not yet implemented")
    }

    override fun submitProgramsList(list: List<ProgramEntity>) {
        TODO("Not yet implemented")
    }

    override fun showNetworkErrorMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun openGroupsList(groupEntity: GroupEntity) {
        TODO("Not yet implemented")
    }

    override fun openChannelsList(channelEntity: ChannelEntity) {
        TODO("Not yet implemented")
    }

    override fun openProgramsList(program: ProgramEntity) {
        TODO("Not yet implemented")
    }

    //endregion
}