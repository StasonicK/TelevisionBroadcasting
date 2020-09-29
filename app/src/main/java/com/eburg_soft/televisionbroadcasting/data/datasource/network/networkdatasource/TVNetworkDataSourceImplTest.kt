package com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource

import com.eburg_soft.televisionbroadcasting.core.datatype.Result
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TestData.TEST_GROUP_RESPONSES
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TestData.generateTestProgramResponses
import io.reactivex.Single

class TVNetworkDataSourceImplTest : TVNetworkDataSource {

    override fun getGroupsAndChannelsFromApi(): Single<Result<List<GroupResponse>>> =
        Single.just(Result.success(TEST_GROUP_RESPONSES))

    override fun getProgramsFromApi(id: String): Single<Result<List<ProgramResponse>>> =
        Single.just(Result.success(generateTestProgramResponses(id.toInt(), 10)))
}