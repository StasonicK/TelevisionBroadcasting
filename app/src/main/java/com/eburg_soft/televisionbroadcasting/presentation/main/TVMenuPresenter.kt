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
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetSelectedChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetSelectedDayUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetSelectedGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetSelectedProgramUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.UpdateChannelsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.UpdateDaysUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.UpdateGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.UpdateProgramsUseCase
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuContract.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.random.Random

class TVMenuPresenter @Inject constructor(
    private val getAllGroupsFromDbUseCase: GetAllGroupsFromDbUseCase,
    private val getChannelsByGroupIdFromDbUseCase: GetChannelsByGroupIdFromDbUseCase,
    private val getProgramsByChannelIdFromDbUseCase: GetProgramsByChannelIdFromDbUseCase,
    private val getDaysFromDbFromDbUseCase: GetAllDaysFromDbUseCase,
    private val getSelectedGroupIdUseCase: GetSelectedGroupIdUseCase,
    private val getSelectedChannelIdUseCase: GetSelectedChannelIdUseCase,
    private val getSelectedProgramUseCase: GetSelectedProgramUseCase,
    private val getSelectedDayUseCase: GetSelectedDayUseCase,
    private val fetchGroupsFromApiToDbUseCase: FetchGroupsFromApiToDbUseCase,
    private val fetchChannelsFromApiToDbUseCase: FetchChannelsFromApiToDbUseCase,
    private val fetchProgramsFromApiToDbUseCase: FetchProgramsFromApiToDbUseCase,
    private val fetchDaysFromApiToDbUseCase: FetchDaysFromApiToDbUseCase,
    private val updateGroupsUseCase: UpdateGroupsUseCase,
    private val updateChannelsUseCase: UpdateChannelsUseCase,
    private val updateProgramsUseCase: UpdateProgramsUseCase,
    private val updateDaysUseCase: UpdateDaysUseCase,
    private val removeAllGroupsUseCase: RemoveAllGroupsUseCase
) : TVMenuContract.Presenter() {

    override fun attach(view: View) {
        super.attach(view)
    }

    override fun syncData() {
        if (isDbEmpty()) {
            fetchAllDataFromApiToDb()
        }
    }

    override fun loadDefaultData() {
        view?.showLoading()

        val groupId = loadGroupsFromDb()
        val channelId = getChannelsListOfSelectedGroupFromDb(groupId)
        getProgramsListOfSelectedChannelFromDb(groupId, channelId)
        loadDaysFromDb()
    }

    override fun loadGroupsFromDb(): String {
        view?.showLoading()
        subscribe(
            getAllGroupsFromDbUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupsList ->
                    view?.submitGroupsList(groupsList)
                    Timber.d("getAllGroupsUseCase accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getAllGroupsUseCase error")
                    view?.hideLoading()
                })
        )
        return ""
    }

    override fun loadChannelsByGroupIdFromDb(groupId: String) {
        view?.showLoading()
        subscribe(
            getChannelsByGroupIdFromDbUseCase
                .execute(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelsList ->
                    view?.submitChannelsList(channelsList)
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
                .subscribe({ programsList ->
                    view?.submitProgramsList(programsList)
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
                .subscribe({ daysList ->
                    view?.submitDaysList(daysList)
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

    fun fetchAllDataFromApiToDb() {
        view?.showLoading()
        subscribe(fetchGroupsFromApiToDbUseCase.execute()
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

    override fun getChannelsListOfSelectedGroupFromDb(savedGroupId: String?): String {
        view?.showLoading()
        var groupId = savedGroupId
        if (groupId.isNullOrBlank()) {
            groupId = getRandomGroupId()
        }

        subscribe(
            getChannelsByGroupIdFromDbUseCase.execute(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelsList ->
                    view?.submitChannelsList(channelsList)
                    view?.saveSelectedGroupId(groupId)
                    view?.hideLoading()
                    Timber.d("getChannelsListOfSelectedGroupFromDb accomplished")

                }, { error ->
                    error.printStackTrace()
                    Timber.d("getChannelsListOfSelectedGroupFromDb error: ${error.message}")
                    view?.hideLoading()
                })
        )
        return ""
    }

    override fun getProgramsListOfSelectedChannelFromDb(savedGroupId: String, savedChannelId: String?) {
        view?.showLoading()
        var channelId = savedChannelId
        if (channelId.isNullOrBlank()) {
            channelId = getRandomChannelIdByGroupId(savedGroupId)
        }
        subscribe(
            getChannelsByGroupIdFromDbUseCase.execute(channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelsList ->
                    view?.submitChannelsList(channelsList)
                    view?.saveSelectedChannelId(channelId)
                    Timber.d("getProgramsListOfSelectedChannelFromDb accomplished")
                    view?.hideLoading()
                }, { error ->
                    error.printStackTrace()
                    Timber.d("getProgramsListOfSelectedChannelFromDb error: ${error.message}")
                    view?.hideLoading()
                }
                )
        )
    }

    private fun getRandomGroupId(): String {
        val groupIdList: MutableSet<String> = HashSet()
        subscribe(
            getAllGroupsFromDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupList ->
                    groupList.forEach {
                        groupIdList.add(it.id)
                    }
                }, {

                })
        )
        val randomValue = Random.nextInt(groupIdList.size)
        return groupIdList.elementAt(randomValue)
    }

    private fun getRandomChannelIdByGroupId(groupId: String): String {
        val channelIdList: MutableSet<String> = HashSet()
        subscribe(
            getChannelsByGroupIdFromDbUseCase.execute(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelList ->
                    channelList.forEach {
                        channelIdList.add(it.id)
                    }
                }, {

                })
        )
        val randomValue = Random.nextInt(channelIdList.size)
        return channelIdList.elementAt(randomValue)
    }
}