package com.eburg_soft.televisionbroadcasting.data.datasource.network

import com.eburg_soft.televisionbroadcasting.data.datasource.network.exceptions.handleNetworkExceptions
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TVNetworkDataSource @Inject constructor(private val tvApi: TVApi) {

    fun getGroupsAndChannelsFromApi(): Single<com.eburg_soft.televisionbroadcasting.core.datatype.Result<List<GroupResponse>>> {
        return Single.create {
            tvApi.getGroupsFromApi()
                .subscribeOn(Schedulers.io())
                .subscribe({ com.eburg_soft.televisionbroadcasting.core.datatype.Result.success(it) }, {
                    com.eburg_soft.televisionbroadcasting.core.datatype.Result.error<Exception>(
                        handleNetworkExceptions(Exception(it))
                    )
                })
        }
    }

    fun getProgramsFromApi(id: String): Single<com.eburg_soft.televisionbroadcasting.core.datatype.Result<List<ProgramResponse>>> {
        return Single.create {
            tvApi.getProgramsFromApi(id)
                .subscribeOn(Schedulers.io())
                .subscribe({ com.eburg_soft.televisionbroadcasting.core.datatype.Result.success(it) }, {
                    com.eburg_soft.televisionbroadcasting.core.datatype.Result.error<Exception>(
                        handleNetworkExceptions(Exception(it))
                    )
                })
        }
    }
}