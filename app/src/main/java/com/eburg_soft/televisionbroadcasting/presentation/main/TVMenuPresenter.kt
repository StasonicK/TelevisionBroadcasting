package com.eburg_soft.televisionbroadcasting.presentation.main

import android.accounts.NetworkErrorException
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveGroupsAndChannelsFromApiToDbReturnIdsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveProgramsFromApiToDbUseCase
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class TVMenuPresenter @Inject constructor(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getChannelsByGroupIdUseCase: GetChannelsByGroupIdUseCase,
    private val getProgramsByChannelIdUseCase: GetProgramsByChannelIdUseCase,
    private val saveGroupsAndChannelsFromApiToDbReturnIdsUseCase: SaveGroupsAndChannelsFromApiToDbReturnIdsUseCase,
    private val saveProgramsFromApiToDbUseCase: SaveProgramsFromApiToDbUseCase,
    private val removeAllGroupsUseCase: RemoveAllGroupsUseCase
) : TVMenuContract.Presenter() {

    override fun attach(view: TVMenuContract.View) {
        this.view = view
    }

    override fun syncData() {
        view?.showLoading()
        subscribe(
            saveGroupsAndChannelsFromApiToDbReturnIdsUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .subscribe({ ids ->
                    ids.forEach { id ->
                        saveProgramsFromApiToDbUseCase.execute("1", id)
                            .subscribe({
                                Timber.d("saveProgramsFromApiToDbUseCase fetched $id")
                            }, { error ->
                                when (error) {
                                    is NetworkErrorException, is UnknownHostException -> {
                                        view?.showNetworkErrorMessage("Error")
                                        Timber.d("saveProgramsFromApiToDbUseCase error")
                                        view?.hideLoading()
                                    }
                                    else -> {
                                        error.printStackTrace()
                                        Timber.d(error.message)
                                        view?.hideLoading()
                                    }
                                }
                            })
                    }
                    Timber.d("getAllGroupsUseCase accomplished")
                    view?.hideLoading()
                }, { error ->

                    when (error) {
                        is NetworkErrorException, is UnknownHostException -> {
                            view?.showNetworkErrorMessage("Error")
                            Timber.d("saveGroupsAndChannelsFromApiToDbReturnIdsUseCase error")
                            view?.hideLoading()
                        }
                        else -> {
                            error.printStackTrace()
                            Timber.d(error.message)
                            view?.hideLoading()
                        }
                    }
                }
                )
        )
    }

    override fun loadGroupsFromDb() {
        view?.showLoading()
        subscribe(
            getAllGroupsUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .subscribe({ list ->
                    view?.submitGroupsList(list)
                    Timber.d("getAllGroupsUseCase accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getAllGroupsUseCase error")
                    view?.hideLoading()
                })
        )
    }

    override fun loadChannelsByGroupIdFromDb(groupId: String) {
        view?.showLoading()
        subscribe(
            getChannelsByGroupIdUseCase
                .execute(groupId)
                .subscribeOn(Schedulers.io())
                .subscribe({ list ->
                    view?.submitChannelsList(list)
                    Timber.d("getChannelsByGroupIdUseCase accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getChannelsByGroupIdUseCase error")
                    view?.hideLoading()
                })
        )
    }

    override fun loadProgramsByChannelIdFromDb(channelId: String) {
        view?.showLoading()
        subscribe(
            getProgramsByChannelIdUseCase
                .execute(channelId)
                .subscribeOn(Schedulers.io())
                .subscribe({ list ->
                    view?.submitProgramsList(list)
                    Timber.d("getProgramsByChannelIdUseCase accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getProgramsByChannelIdUseCase error")
                    view?.hideLoading()
                })
        )
    }

    override fun removeAllGroups() {
        view?.showLoading()
        subscribe(
            removeAllGroupsUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.d("removeAllGroupsUseCase accomplished")
                    view?.hideLoading()
                }, {
                    Timber.d("removeAllGroupsUseCase error")
                    view?.hideLoading()
                })
        )
    }
}