package com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource

import com.eburg_soft.televisionbroadcasting.core.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TestDataNetwork.TEST_GROUP_RESPONSES
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TestDataNetwork.generateTestProgramResponses
import io.reactivex.Single
import javax.inject.Inject

class TVNetworkDataSourceImplTest @Inject constructor() : TVNetworkDataSource {

    override fun getGroupsAndChannelsFromApi(): Single<List<GroupResponse>> =
        Single.just(TEST_GROUP_RESPONSES)

    override fun getProgramsFromApi(id: String): Single<List<ProgramResponse>> =
        Single.just(generateTestProgramResponses(id.toInt(), 10))
}