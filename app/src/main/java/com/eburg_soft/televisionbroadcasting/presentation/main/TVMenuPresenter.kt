package com.eburg_soft.televisionbroadcasting.presentation.main

import android.accounts.NetworkErrorException
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchChannelsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchDaysFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchGroupsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchProgramsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllDaysFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuContract.View
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class TVMenuPresenter @Inject constructor(
    private val getAllGroupsFromDbUseCase: GetAllGroupsFromDbUseCase,
    private val getChannelsByGroupIdFromDbUseCase: GetChannelsByGroupIdFromDbUseCase,
    private val getProgramsByChannelIdFromDbUseCase: GetProgramsByChannelIdFromDbUseCase,
    private val getDaysFromDbFromDbUseCase: GetAllDaysFromDbUseCase,
    private val fetchGroupsFromApiToDbUseCase: FetchGroupsFromApiToDbUseCase,
    private val fetchChannelsFromApiToDbUseCase: FetchChannelsFromApiToDbUseCase,
    private val fetchProgramsFromApiToDbUseCase: FetchProgramsFromApiToDbUseCase,
    private val fetchDaysFromApiToDbUseCase: FetchDaysFromApiToDbUseCase,
    private val removeAllGroupsUseCase: RemoveAllGroupsUseCase
) : TVMenuContract.Presenter() {

    override fun attach(view: View) {
        super.attach(view)
//        syncData()
    }

    override fun syncData() {
        if (isDbEmpty()) {
            fetchAllDataFromApiToDb()
        }
//        view?.populateRecyclers()
    }

//    override fun fetchGroupsWithChannelsFromApiIntoDb() {
//        view?.showLoading()
//        subscribe(
//            fetchGroupsFromApiToDbUseCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ channelSet ->
//                    Timber.d("fetchGroupsFromApiToDbUseCase accomplished")
//                    fetchChannelsFromApiToDbUseCase.execute(channelSet)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({
//
//                            Timber.d("fetchChannelsFromApiToDbUseCase accomplished")
//                        }, { error ->
//                            when (error) {
//                                is NetworkErrorException, is UnknownHostException -> {
//                                    view?.showNetworkErrorMessage("Error")
//                                    Timber.d("fetchChannelsFromApiToDbUseCase network error")
//                                    view?.hideLoading()
//                                }
//                                else -> {
//                                    error.printStackTrace()
//                                    Timber.d("fetchChannelsFromApiToDbUseCase error: ${error.message}")
//                                    view?.hideLoading()
//                                }
//                            }
//                        })
//                }, { error ->
//                    when (error) {
//                        is NetworkErrorException, is UnknownHostException -> {
//                            view?.showNetworkErrorMessage("Error")
//                            Timber.d("fetchGroupsFromApiToDbUseCase network error")
//                            view?.hideLoading()
//                        }
//                        else -> {
//                            error.printStackTrace()
//                            Timber.d("fetchGroupsFromApiToDbUseCase error: ${error.message}")
//                            view?.hideLoading()
//                        }
//                    }
//                }
//            )
//        )
//    }
//
//    override fun fetchProgramsByChannelIdFromApiIntoDb(channelIdList: List<String>) {
//        fetchProgramsFromApiToDbUseCase.execute("1", channelIdList)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//
//                Timber.d("fetchProgramsFromApiToDbUseCase accomplished")
//            }, { error ->
//                when (error) {
//                    is NetworkErrorException, is UnknownHostException -> {
//                        view?.showNetworkErrorMessage("Error")
//                        Timber.d("fetchProgramsFromApiToDbUseCase network error")
//                        view?.hideLoading()
//                    }
//                    else -> {
//                        error.printStackTrace()
//                        Timber.d("fetchProgramsFromApiToDbUseCase error: ${error.message}")
//                        view?.hideLoading()
//                    }
//                }
//            })
//
//        Timber.d("fetchChannelsFromApiToDbUseCase accomplished")
//    }
//
//    override fun fetchDaysFromApiIntoDb() {
//        subscribe(
//            fetchDaysFromApiToDbUseCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ dayList ->
//                    view?.submitDaysList(dayList)
//                    Timber.d("fetchDaysFromApiToDbUseCase accomplished")
//                }, { error ->
//                    when (error) {
//                        is NetworkErrorException, is UnknownHostException -> {
//                            view?.showNetworkErrorMessage("Error")
//                            Timber.d("fetchDaysFromApiToDbUseCase network error")
//                            view?.hideLoading()
//                        }
//                        else -> {
//                            error.printStackTrace()
//                            Timber.d("fetchDaysFromApiToDbUseCase error: ${error.message}")
//                            view?.hideLoading()
//                        }
//                    }
//                })
//        )
//    }

    override fun loadGroupsFromDb() {
        view?.showLoading()
        subscribe(
            getAllGroupsFromDbUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupList ->
                    val id = groupList[0].id
                    view?.submitDefaultGroupId(id)
                    Timber.d("submitDefaultGroupId: $id")
                    view?.showGroupsRecycler()
                    view?.submitGroupList(groupList)
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
            getChannelsByGroupIdFromDbUseCase
                .execute(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(100, MILLISECONDS)
                .subscribe({ channelList ->
                    val id = channelList[0].id
                    view?.submitDefaultChannelId(id)
                    Timber.d("submitDefaultChannelId: $id")
                    view?.showChannelsRecycler()
                    view?.submitChannelList(channelList)
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
            getProgramsByChannelIdFromDbUseCase
                .execute(channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(100, MILLISECONDS)
                .subscribe({ programList ->
//                    view?.showProgramsRecycler()
                    view?.submitProgramList(programList)
                    Timber.d("getProgramsByChannelIdUseCase accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getProgramsByChannelIdUseCase error")
                    view?.hideLoading()
                })
        )
    }

    override fun loadDaysFromDb() {
        view?.showLoading()
        subscribe(
            getDaysFromDbFromDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(100, MILLISECONDS)
                .subscribe({ dayList ->
//                    view?.showDaysRecycler()
                    view?.submitDaysList(dayList)
                    Timber.d("loadDaysFromDb accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("loadDaysFromDb error")
                    view?.hideLoading()
                })
        )
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
            getAllGroupsFromDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { list.addAll(it) }
        )
        return list.isEmpty()
    }

    //    fun fetchGroupsChannelsProgramsFromApiToDb():Disposable {
//        return  fetchGroupsFromApiToDbUseCase.execute()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ channelSet ->
//                fetchChannelsFromApiToDbUseCase.execute(channelSet)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        fetchProgramsFromApiToDbUseCase.execute("1", it)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe({
//
//                                Timber.d("fetchProgramsFromApiToDbUseCase accomplished")
//                            }, { error ->
//                                when (error) {
//                                    is NetworkErrorException, is UnknownHostException -> {
//                                        view?.showNetworkErrorMessage("Error")
//                                        Timber.d("fetchProgramsFromApiToDbUseCase network error")
//                                        view?.hideLoading()
//                                    }
//                                    else -> {
//                                        error.printStackTrace()
//                                        Timber.d("fetchProgramsFromApiToDbUseCase error: ${error.message}")
//                                        view?.hideLoading()
//                                    }
//                                }
//                            })
//
//                        Timber.d("fetchChannelsFromApiToDbUseCase accomplished")
//                    }, { error ->
//                        when (error) {
//                            is NetworkErrorException, is UnknownHostException -> {
//                                view?.showNetworkErrorMessage("Error")
//                                Timber.d("fetchChannelsFromApiToDbUseCase network error")
//                                view?.hideLoading()
//                            }
//                            else -> {
//                                error.printStackTrace()
//                                Timber.d("fetchGroupsFromApiToDbReturnChannelChannelIdsUseCase error: ${error.message}")
//                                view?.hideLoading()
//                            }
//                        }
//                    })
//
//                Timber.d("fetchGroupsFromApiToDbUseCase accomplished")
//            }, { error ->
//                when (error) {
//                    is NetworkErrorException, is UnknownHostException -> {
//                        view?.showNetworkErrorMessage("Error")
//                        Timber.d("fetchGroupsFromApiToDbReturnChannelChannelIdsUseCase network error")
//                        view?.hideLoading()
//                    }
//                    else -> {
//                        error.printStackTrace()
//                        Timber.d("fetchGroupsFromApiToDbReturnChannelChannelIdsUseCase error: ${error.message}")
//                        view?.hideLoading()
//                    }
//                }
//            }
//            )
//    }
    fun fetchAllDataFromApiToDb() {
        view?.showLoading()
//        return fetchGroupsChannelsProgramsFromApiToDb()

        subscribe(
            fetchGroupsFromApiToDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelSet ->
                    fetchChannelsFromApiToDbUseCase.execute(channelSet)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            fetchProgramsFromApiToDbUseCase.execute("1", it)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({

                                    Timber.d("fetchProgramsFromApiToDbUseCase accomplished")
                                }, { error ->
                                    when (error) {
                                        is NetworkErrorException, is UnknownHostException -> {
                                            view?.showNetworkErrorMessage("Error")
                                            Timber.d("fetchProgramsFromApiToDbUseCase network error")
                                            view?.hideLoading()
                                        }
                                        else -> {
                                            error.printStackTrace()
                                            Timber.d("fetchProgramsFromApiToDbUseCase error: ${error.message}")
                                            view?.hideLoading()
                                        }
                                    }
                                })

                            Timber.d("fetchChannelsFromApiToDbUseCase accomplished")
                        }, { error ->
                            when (error) {
                                is NetworkErrorException, is UnknownHostException -> {
                                    view?.showNetworkErrorMessage("Error")
                                    Timber.d("fetchChannelsFromApiToDbUseCase network error")
                                    view?.hideLoading()
                                }
                                else -> {
                                    error.printStackTrace()
                                    Timber.d("fetchGroupsFromApiToDbReturnChannelChannelIdsUseCase error: ${error.message}")
                                    view?.hideLoading()
                                }
                            }
                        })

                    Timber.d("fetchGroupsFromApiToDbUseCase accomplished")
                }, { error ->
                    when (error) {
                        is NetworkErrorException, is UnknownHostException -> {
                            view?.showNetworkErrorMessage("Error")
                            Timber.d("fetchGroupsFromApiToDbReturnChannelChannelIdsUseCase network error")
                            view?.hideLoading()
                        }
                        else -> {
                            error.printStackTrace()
                            Timber.d("fetchGroupsFromApiToDbReturnChannelChannelIdsUseCase error: ${error.message}")
                            view?.hideLoading()
                        }
                    }
                }
                )
        )

        subscribe(
//         val disposableFetchDaysFromApiToDb=
            fetchDaysFromApiToDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dayList ->
                    view?.submitDaysList(dayList)
                    Timber.d("fetchDaysFromApiToDbUseCase accomplished")
                }, { error ->
                    when (error) {
                        is NetworkErrorException, is UnknownHostException -> {
                            view?.showNetworkErrorMessage("Error")
                            Timber.d("fetchDaysFromApiToDbUseCase network error")
                            view?.hideLoading()
                        }
                        else -> {
                            error.printStackTrace()
                            Timber.d("fetchDaysFromApiToDbUseCase error: ${error.message}")
                            view?.hideLoading()
                        }
                    }
                })
        )
    }

//    fun fetchDaysFromApiToDb():Disposable{
//        return  fetchDaysFromApiToDbUseCase.execute()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ dayList ->
//                view?.submitDaysList(dayList)
//                Timber.d("fetchDaysFromApiToDbUseCase accomplished")
//            }, { error ->
//                when (error) {
//                    is NetworkErrorException, is UnknownHostException -> {
//                        view?.showNetworkErrorMessage("Error")
//                        Timber.d("fetchDaysFromApiToDbUseCase network error")
//                        view?.hideLoading()
//                    }
//                    else -> {
//                        error.printStackTrace()
//                        Timber.d("fetchDaysFromApiToDbUseCase error: ${error.message}")
//                        view?.hideLoading()
//                    }
//                }
//            })
//    }

    override fun loadAllData() {
        view?.showLoading()
//        subscribe(
//        val disposableLoadAllData =

//            getAllGroupsFromDbUseCase
//                .execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap { list ->
////                .subscribe({ list ->
//                    val id = list[0].id
//                    view?.submitDefaultGroupId(id)
//                    Timber.d("submitDefaultGroupId: $id")
//                    view?.showGroupsRecycler()
//
//                    getChannelsByGroupIdFromDbUseCase.execute(id)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap { channelList ->
////                    .subscribe({ channelList ->
//                            val id = channelList[0].id
//                            view?.submitDefaultChannelId(id)
//                            Timber.d("submitDefaultChannelId: $id")
//                            view?.showChannelsRecycler()
//                            view?.submitChannelList(channelList)
//                            Timber.d("getChannelsByGroupIdUseCase accomplished")
//                            view?.hideLoading()
//
//                            getProgramsByChannelIdFromDbUseCase.execute(id)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .flatMap { programList ->
////                            .subscribe({ programList ->
//                                    view?.submitProgramList(programList)
//                                    Timber.d("getProgramsByChannelIdUseCase accomplished")
//                                    view?.hideLoading()
//
//                                    getDaysFromDbFromDbUseCase.execute()
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .map { dayList ->
//                                            view?.submitDaysList(dayList)
//                                        }
////                                        .doOnComplete {
////
////                                            Timber.d("getDaysFromDbUseCase accomplished")
////                                            view?.hideLoading()
////
////                                        }
//                                        .subscribe()
//                                }.subscribe()
//                        }.subscribe()
//                }

//        )
    }

//                                    .subscribe({ dayList ->
////                    view?.showDaysRecycler()
//                                        view?.submitDaysList(dayList)
//                                        Timber.d("getDaysFromDbUseCase accomplished")
//                                        view?.hideLoading()
//                                    }, {
//                                        it.printStackTrace()
//                                        Timber.d("getDaysFromDbUseCase error")
//                                        view?.hideLoading()
//                                    })
//
//                            }, { error ->
//                                when (error) {
//                                    is NetworkErrorException, is UnknownHostException -> {
//                                        view?.showNetworkErrorMessage("Error")
//                                        Timber.d("getProgramsByChannelIdUseCase network error")
//                                        view?.hideLoading()
//                                    }
//                                    else -> {
//                                        error.printStackTrace()
//                                        Timber.d("getProgramsByChannelIdUseCase error: ${error.message}")
//                                        view?.hideLoading()
//                                    }
//                                }
//                            })
//                    }, { error ->
//                        when (error) {
//                            is NetworkErrorException, is UnknownHostException -> {
//                                view?.showNetworkErrorMessage("Error")
//                                Timber.d("getChannelsByGroupIdUseCase network error")
//                                view?.hideLoading()
//                            }
//                            else -> {
//                                error.printStackTrace()
//                                Timber.d("getChannelsByGroupIdUseCase error: ${error.message}")
//                                view?.hideLoading()
//                            }
//                        }
//                    })
//            }, { error ->
//                when (error) {
//                    is NetworkErrorException, is UnknownHostException -> {
//                        view?.showNetworkErrorMessage("Error")
//                        Timber.d("getAllGroupsUseCase network error")
//                        view?.hideLoading()
//                    }
//                    else -> {
//                        error.printStackTrace()
//                        Timber.d("getAllGroupsUseCase error: ${error.message}")
//                        view?.hideLoading()
//                    }
//                }
//            }
//            )

//                .subscribe({ list ->
//                    val id = list[0].id
//                    view?.submitDefaultGroupId(id)
//                    Timber.d("submitDefaultGroupId: $id")
//                    view?.showGroupsRecycler()
//                    view?.submitGroupList(list)
//                    Timber.d("getAllGroupsUseCase accomplished")
//                    view?.hideLoading()
//                }, {
//                    it.printStackTrace()
//                    Timber.d("getAllGroupsUseCase error")
//                    view?.hideLoading()
//                })

//                                    )
//                                }
}