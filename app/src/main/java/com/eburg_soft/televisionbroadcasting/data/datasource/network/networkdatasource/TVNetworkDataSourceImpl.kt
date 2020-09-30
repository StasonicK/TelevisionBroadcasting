package com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource

import com.eburg_soft.televisionbroadcasting.core.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.datasource.network.TVApi
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TVNetworkDataSourceImpl @Inject constructor(private val tvApi: TVApi) : TVNetworkDataSource {

    override fun getGroupsAndChannelsFromApi(): Single<List<GroupResponse>> {
        return Single.create {
            tvApi.getGroupsFromApi()
                .subscribeOn(Schedulers.io())
        }
    }

    override fun getProgramsFromApi(id: String): Single<List<ProgramResponse>> {
        return Single.create {
            tvApi.getProgramsFromApi(id)
                .subscribeOn(Schedulers.io())
        }
    }
}