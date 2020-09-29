package com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource

import com.eburg_soft.televisionbroadcasting.core.datatype.Result
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import io.reactivex.Single

interface TVNetworkDataSource {

    fun getGroupsAndChannelsFromApi(): Single<Result<List<GroupResponse>>>
    fun getProgramsFromApi(id: String): Single<Result<List<ProgramResponse>>>
}
