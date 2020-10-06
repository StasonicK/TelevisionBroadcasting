package com.eburg_soft.televisionbroadcasting.data.datasource.network

import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TVApi {

    //    @GET("/tv/group/")
    @GET("json/group.json")
    fun getGroupsFromApi(): Single<List<GroupResponse>>

    //    @GET("/tv/program/{channelId}")
    @GET("json/program_{channelId}.json")
    fun getProgramsFromApi(@Path("channelId") channelId: String): Single<List<ProgramResponse>>
}