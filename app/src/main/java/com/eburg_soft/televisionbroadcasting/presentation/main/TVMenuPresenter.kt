package com.eburg_soft.televisionbroadcasting.presentation.main

import android.accounts.NetworkErrorException
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
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
import com.eburg_soft.televisionbroadcasting.presentation.utils.getRandomElementFromList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException
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

    override fun syncData() {
        if (isDbEmpty()) {
            fetchGroupsFromApiIntoDb()
        }
    }

    override fun fetchGroupsFromApiIntoDb() {
        view?.showLoading()
        subscribe(fetchGroupsFromApiToDbUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("fetchGroupsFromApiToDbUseCase accomplished")
                fetchChannelsByChannelSetFromApiIntoDb(it)
            }, { error ->
                when (error) {
                    is NetworkErrorException, is UnknownHostException -> {
                        view?.showNetworkErrorMessage("Error")
                        Timber.d("fetchGroupsFromApiToDbUseCase network error")
                        view?.hideLoading()
                    }
                    else -> {
                        error.printStackTrace()
                        Timber.d("fetchGroupsFromApiToDbUseCase error: ${error.message}")
                        view?.hideLoading()
                    }
                }
            }
            )
        )
    }

    override fun fetchChannelsByChannelSetFromApiIntoDb(channelSet: Set<ChannelEntity>) {
        view?.showLoading()
        subscribe(fetchChannelsFromApiToDbUseCase.execute(channelSet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("fetchChannelsByChannelSetFromApiIntoDb accomplished")
                fetchProgramsByChannelIdFromApiIntoDb(it)
            }, { error ->
                when (error) {
                    is NetworkErrorException, is UnknownHostException -> {
                        view?.showNetworkErrorMessage("Error")
                        Timber.d("fetchGroupsFromApiToDbUseCase network error")
                        view?.hideLoading()
                    }
                    else -> {
                        error.printStackTrace()
                        Timber.d("fetchGroupsFromApiToDbUseCase error: ${error.message}")
                        view?.hideLoading()
                    }
                }
            }
            )
        )
    }

    override fun fetchProgramsByChannelIdFromApiIntoDb(channelIdList: List<String>) {
        view?.showLoading()
        subscribe(fetchProgramsFromApiToDbUseCase.execute("1", channelIdList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("fetchProgramsFromApiToDbUseCase")
                fetchDaysFromApiIntoDb()
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
            }
            )
        )
    }

    override fun fetchDaysFromApiIntoDb() {
        view?.showLoading()
        subscribe(
            fetchDaysFromApiToDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ daysList ->
                    view?.submitDaysList(daysList)
                    Timber.d("submitDaysList accomplished")

                    view?.populateGroupsRecycler()
                    Timber.d("populateGroupsRecycler accomplished")

                    Timber.d("fetchDaysFromApiIntoDb accomplished")
                }, { error ->
                    when (error) {
                        is NetworkErrorException, is UnknownHostException -> {
                            view?.showNetworkErrorMessage("Error")
                            Timber.d("fetchDaysFromApiIntoDb network error")
                            view?.hideLoading()
                        }
                        else -> {
                            error.printStackTrace()
                            Timber.d("fetchDaysFromApiIntoDb error: ${error.message}")
                            view?.hideLoading()
                        }
                    }
                }
                )
        )
    }

    override fun fetchGroupsFromDb(selectedGroupId: String?) {
        if (selectedGroupId.isNullOrBlank()) {
            getRandomGroupIdAndLoadGroupsFromDb()
        } else {
            loadGroupsFromDb()
        }
    }

    override fun fetchChannelsFromDb(selectedGroupId: String, selectedChannelId: String?) {
        if (selectedChannelId.isNullOrBlank()) {
            getRandomChannelIdAndLoadChannelsByGroupId(selectedGroupId)
        } else {
            loadChannelsByGroupIdFromDb(selectedGroupId)
        }
    }

    override fun fetchProgramsFromDb(selectedChannelId: String, selectedProgramId: String?) {
        if (selectedProgramId.isNullOrBlank()) {
            getRandomProgramIdByChannelIdAndLoadProgramsByChannelId(selectedChannelId)
        } else {
            loadProgramsByChannelIdFromDb(selectedChannelId)
        }
    }

    override fun fetchDayFromDb(selectedDayId: String?) {
        if (selectedDayId.isNullOrBlank()) {
            getRandomDayId()
        } else {
            loadDaysFromDb()
        }
    }

    override fun loadGroupsFromDb() {
        view?.showLoading()
        subscribe(
            getAllGroupsFromDbUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupsList ->

                    view?.initGroupsRecycler()
                    Timber.d("initGroupsRecycler accomplished")

                    view?.submitGroupsList(groupsList)
                    Timber.d("submitGroupsList accomplished")


                    Timber.d("loadGroupsFromDb accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("loadGroupsFromDb error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun loadChannelsByGroupIdFromDb(savedGroupId: String) {
        view?.showLoading()
        subscribe(
            getChannelsByGroupIdFromDbUseCase
                .execute(savedGroupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelsList ->

                    view?.initChannelsRecycler()
                    Timber.d("initChannelsRecycler accomplished")

                    view?.submitChannelsList(channelsList)
                    Timber.d("submitChannelsList accomplished")

                    Timber.d("loadChannelsByGroupIdFromDb accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("loadChannelsByGroupIdFromDb error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun loadProgramsByChannelIdFromDb(savedChannelId: String) {
        view?.showLoading()
        subscribe(
            getProgramsByChannelIdFromDbUseCase
                .execute(savedChannelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ programsList ->

                    view?.initProgramsRecycler()
                    Timber.d("initProgramsRecycler accomplished")

                    view?.submitProgramsList(programsList)
                    Timber.d("submitProgramsList accomplished")

                    Timber.d("loadProgramsByChannelIdFromDb accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("loadProgramsByChannelIdFromDb error: ${it.message}")
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

                    view?.initDaysRecycler()
                    Timber.d("initDaysRecycler accomplished")

                    view?.submitDaysList(daysList)
                    Timber.d("submitDaysList")

                    view?.hideLoading()
                    Timber.d("loadDaysFromDb accomplished")
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
                    view?.hideLoading()
                    Timber.d("removeAllGroups accomplished")
                }, {
                    Timber.d("removeAllGroups error")
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
                .subscribe({
                    list.addAll(it)
                    Timber.d("isDbEmpty accomplished")
                }, {
                    Timber.d("isDbEmpty error")
                    view?.hideLoading()
                })
        )
        return list.isEmpty()
    }

    override fun getRandomGroupIdAndLoadGroupsFromDb() {
        subscribe(
            getAllGroupsFromDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupsList ->
                    val groupIdsList: MutableList<String> = ArrayList()
                    groupsList.forEach {
                        groupIdsList.add(it.id)
                    }
                    val groupId = getRandomElementFromList(groupIdsList)
                    view?.saveSelectedGroupId(groupId)
                    Timber.d("saveSelectedGroupId accomplished")

                    view?.initGroupsRecycler()
                    Timber.d("initGroupsRecycler accomplished")

                    view?.submitGroupsList(groupsList)
                    Timber.d("submitGroupsList")

                    Timber.d("getRandomGroupId accomplished")
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomGroupId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun getRandomChannelIdAndLoadChannelsByGroupId(groupId: String) {
        subscribe(
            getChannelsByGroupIdFromDbUseCase.execute(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelsList ->
                    val channelIdsList: MutableList<String> = ArrayList()
                    channelsList.forEach {
                        channelIdsList.add(it.id)
                    }
                    val channelId = getRandomElementFromList(channelIdsList)
                    view?.saveSelectedChannelId(channelId)
                    Timber.d("saveSelectedChannelId accomplished")

                    view?.initChannelsRecycler()
                    Timber.d("initChannelsRecycler accomplished")

                    view?.submitChannelsList(channelsList)
                    Timber.d("submitChannelsList")

                    Timber.d("getRandomChannelIdByGroupId accomplished")
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomChannelIdByGroupId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun getRandomProgramIdByChannelIdAndLoadProgramsByChannelId(channelId: String) {
        subscribe(
            getProgramsByChannelIdFromDbUseCase.execute(channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ programsList ->
                    val programIdsList: MutableList<String> = ArrayList()
                    programsList.forEach {
                        programIdsList.add(it.id)
                    }
                    val programId = getRandomElementFromList(programIdsList)
                    view?.saveSelectedProgramId(programId)
                    Timber.d("saveSelectedProgramId accomplished")

                    view?.initProgramsRecycler()
                    Timber.d("initProgramsRecycler accomplished")

                    view?.submitProgramsList(programsList)
                    Timber.d("submitProgramsList")

                    Timber.d("getRandomProgramIdByGroupId accomplished")
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomProgramIdByGroupId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun getRandomDayId() {
        subscribe(
            getDaysFromDbFromDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ daysList ->
                    val dayIdsList: MutableList<String> = ArrayList()
                    daysList.forEach {
                        dayIdsList.add(it.id)
                    }
                    val dayId = getRandomElementFromList(dayIdsList)
                    view?.saveSelectedDayId(dayId)
                    Timber.d("saveSelectedDayId accomplished")

                    view?.initDaysRecycler()
                    Timber.d("initDaysRecycler accomplished")

                    view?.submitDaysList(daysList)
                    Timber.d("submitDaysList")

                    Timber.d("getRandomDayId accomplished")
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomDayId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }
}