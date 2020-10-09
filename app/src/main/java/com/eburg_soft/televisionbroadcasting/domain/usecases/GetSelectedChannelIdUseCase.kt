package com.eburg_soft.televisionbroadcasting.domain.usecases

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import javax.inject.Inject

class GetSelectedChannelIdUseCase @Inject constructor(private val repository: TVRepository) {

//    fun execute(limit: Int = 1, selected: Boolean = true): Flowable<String> =
////        Flowable<List<ChannelEntity>> =
//        repository.getSelectedChannelById(limit, selected)
}