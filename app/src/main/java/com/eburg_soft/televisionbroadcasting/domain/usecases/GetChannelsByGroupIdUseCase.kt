package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetChannelsByGroupIdUseCase //@Inject constructor()
@Inject constructor(private val repository: TVRepository)
{

//    @Inject
//    lateinit var repository: TVRepository
    fun execute(groupId: String): Flowable<List<ChannelEntity>> = repository.getChannelsByGroupId(groupId)
}