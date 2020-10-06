package com.eburg_soft.televisionbroadcasting.presentation.main

import android.accounts.NetworkErrorException
import com.eburg_soft.televisionbroadcasting.data.datasource.database.TestDataDb
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveChannelsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveGroupsFromApiToDbReturnChannelIdsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveProgramsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuContract.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class TVMenuPresenter @Inject constructor(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getChannelsByGroupIdUseCase: GetChannelsByGroupIdUseCase,
    private val getProgramsByChannelIdUseCase: GetProgramsByChannelIdUseCase,
    private val saveGroupsFromApiToDbReturnChannelChannelIdsUseCase: SaveGroupsFromApiToDbReturnChannelIdsUseCase,
    private val saveChannelsFromApiToDbUseCase: SaveChannelsFromApiToDbUseCase,
    private val saveProgramsFromApiToDbUseCase: SaveProgramsFromApiToDbUseCase,
    private val removeAllGroupsUseCase: RemoveAllGroupsUseCase
) : TVMenuContract.Presenter() {

    override fun attach(view: View) {
        super.attach(view)
        syncData()
    }

    override fun syncData() {
        if (isDbEmpty()) {
            saveAllDataFromApiToDb()
        }
    }

    override fun loadGroupsFromDb() {
        view?.showLoading()
        subscribe(
            getAllGroupsUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                .observeOn(AndroidSchedulers.mainThread())
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
                .observeOn(AndroidSchedulers.mainThread())
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

    override fun loadDaysByChannelIdFromDb() {
        view?.showLoading()
        view?.submitDaysList(TestDataDb.generateDayEntities("01.06.2020", "14.06.2020"))
        view?.hideLoading()
    }

    override fun removeAllGroups() {
        view?.showLoading()
        subscribe(
            removeAllGroupsUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    Timber.d("removeAllGroupsUseCase accomplished")
                    view?.hideLoading()
                }, {
                    Timber.d("removeAllGroupsUseCase error")
                    view?.hideLoading()
                })
        )
    }

    private fun isDbEmpty(): Boolean {
        val list = arrayListOf<GroupEntity>()
        subscribe(
            getAllGroupsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { list.addAll(it) }
        )
        return list.isEmpty()
    }

    private fun saveAllDataFromApiToDb() {
        view?.showLoading()
        subscribe(
//            saveGroupsFromApiToDbReturnChannelChannelIdsUseCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap {
//                    saveChannelsFromApiToDbUseCase.execute(it)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap { ids ->
//                            saveProgramsFromApiToDbUseCase.execute("1", ids)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                        }
//                }
//                .subscribe()
            saveGroupsFromApiToDbReturnChannelChannelIdsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    saveChannelsFromApiToDbUseCase.execute(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            saveProgramsFromApiToDbUseCase.execute("1", it)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({

                                    Timber.d("saveProgramsFromApiToDbUseCase accomplished")
                                }, { error ->
                                    when (error) {
                                        is NetworkErrorException, is UnknownHostException -> {
                                            view?.showNetworkErrorMessage("Error")
                                            Timber.d("saveProgramsFromApiToDbUseCase network error")
                                            view?.hideLoading()
                                        }
                                        else -> {
                                            error.printStackTrace()
                                            Timber.d("saveProgramsFromApiToDbUseCase error: ${error.message}")
                                            view?.hideLoading()
                                        }
                                    }
                                })

                            Timber.d("saveChannelsFromApiToDbUseCase accomplished")
                        }, { error ->
                            when (error) {
                                is NetworkErrorException, is UnknownHostException -> {
                                    view?.showNetworkErrorMessage("Error")
                                    Timber.d("saveChannelsFromApiToDbUseCase network error")
                                    view?.hideLoading()
                                }
                                else -> {
                                    error.printStackTrace()
                                    Timber.d("saveGroupsFromApiToDbReturnChannelChannelIdsUseCase error: ${error.message}")
                                    view?.hideLoading()
                                }
                            }
                        })

                    Timber.d("saveProgramsFromApiToDbUseCase accomplished")
                }, { error ->
                    when (error) {
                        is NetworkErrorException, is UnknownHostException -> {
                            view?.showNetworkErrorMessage("Error")
                            Timber.d("saveGroupsFromApiToDbReturnChannelChannelIdsUseCase network error")
                            view?.hideLoading()
                        }
                        else -> {
                            error.printStackTrace()
                            Timber.d("saveGroupsFromApiToDbReturnChannelChannelIdsUseCase error: ${error.message}")
                            view?.hideLoading()
                        }
                    }
                }
                )
        )
    }
}