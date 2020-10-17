package com.eburg_soft.televisionbroadcasting.presentation.main

import android.accounts.NetworkErrorException
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchChannelsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchDaysFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchGroupsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchProgramsFromApiIntoDbUseCase
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
    private val fetchGroupsFromApiIntoDbUseCase: FetchGroupsFromApiIntoDbUseCase,
    private val fetchChannelsFromApiIntoDbUseCase: FetchChannelsFromApiIntoDbUseCase,
    private val fetchProgramsFromApiIntoDbUseCase: FetchProgramsFromApiIntoDbUseCase,
    private val fetchDaysFromApiIntoDbUseCase: FetchDaysFromApiIntoDbUseCase,
    private val removeAllGroupsUseCase: RemoveAllGroupsUseCase
) : TVMenuContract.Presenter() {

    //region ====================== 1. verify data in tables ======================

    //endregion
    override fun syncData() {
        if (isDbEmpty()) {
            fetchGroupsFromApiIntoDb()
        }
    }

    //region ====================== 2. fetch data from Api and save it into Db ======================

    override fun fetchGroupsFromApiIntoDb() {
        view?.showLoading()
        subscribe(fetchGroupsFromApiIntoDbUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fetchChannelsByChannelSetFromApiIntoDb(it)
                Timber.d("fetchGroupsFromApiToDbUseCase accomplished")
//                view?.initGroupsRecycler()
//                Timber.d("initGroupsRecycler accomplished")
//                view?.populateGroupsRecycler()
//                Timber.d("populateGroupsRecycler accomplished")
                view?.hideLoading()
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
        subscribe(fetchChannelsFromApiIntoDbUseCase.execute(channelSet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fetchProgramsByChannelIdFromApiIntoDb(it)
                Timber.d("fetchChannelsByChannelSetFromApiIntoDb accomplished")
//                view?.initChannelsRecycler()
//                Timber.d("initChannelsRecycler accomplished")
//                view?.populateChannelsRecycler()
//                Timber.d("populateChannelsRecycler accomplished")
                view?.hideLoading()
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
        subscribe(fetchProgramsFromApiIntoDbUseCase.execute("1", channelIdList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fetchDaysFromApiIntoDb()
                Timber.d("fetchProgramsFromApiToDbUseCase")
//                view?.initProgramsRecycler()
//                Timber.d("initProgramsRecycler accomplished")
//                view?.populateProgramsRecycler()
//                Timber.d("populateProgramsRecycler accomplished")
                view?.hideLoading()
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
            fetchDaysFromApiIntoDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
//                    view?.initDaysRecycler()
//                    Timber.d("initDaysRecycler accomplished")
//                    view?.populateDaysRecycler()
//                    Timber.d("populateDaysRecycler accomplished")
                    Timber.d("fetchDaysFromApiIntoDb accomplished")
                    view?.hideLoading()
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
    //endregion

    //region ====================== 3. verify selected Id and load  data from DB ======================

    override fun verifySelectedIdAndLoadGroupsFromDb(selectedGroupId: String?) {
        if (selectedGroupId.isNullOrBlank()) {
            getRandomGroupIdAndLoadGroupsFromDb()
        } else {
            loadGroupsFromDb()
        }
    }

    override fun verifySelectedIdAndLoadChannelsFromDb(selectedGroupId: String, selectedChannelId: String?) {
        if (selectedChannelId.isNullOrBlank()) {
            getRandomChannelIdAndLoadChannelsByGroupId(selectedGroupId)
        } else {
            loadChannelsByGroupIdFromDb(selectedGroupId)
        }
    }

    override fun verifySelectedIdAndLoadProgramsFromDb(selectedChannelId: String, selectedProgramId: String?) {
        if (selectedProgramId.isNullOrBlank()) {
            getRandomProgramIdByChannelIdAndLoadProgramsByChannelId(selectedChannelId)
        } else {
            loadProgramsByChannelIdFromDb(selectedChannelId)
        }
    }

    override fun verifySelectedIdAndLoadDaysFromDb(selectedDayId: String?) {
        if (selectedDayId.isNullOrBlank()) {
            getRandomDayId()
        } else {
            loadDaysFromDb(selectedDayId)
        }
    }

    //endregion

    //region ====================== 4.1. get random Id, save Id, load data from DB and submit it adapters ======================

    override fun getRandomGroupIdAndLoadGroupsFromDb() {
        view?.showLoading()
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
                    view?.submitGroupsList(groupsList)
                    Timber.d("submitGroupsList")
                    Timber.d("getRandomGroupId accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomGroupId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun getRandomChannelIdAndLoadChannelsByGroupId(groupId: String) {
        view?.showLoading()
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
                    view?.submitChannelsList(channelsList)
                    Timber.d("submitChannelsList")
                    Timber.d("getRandomChannelIdByGroupId accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomChannelIdByGroupId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun getRandomProgramIdByChannelIdAndLoadProgramsByChannelId(channelId: String) {
        view?.showLoading()
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
                    view?.submitProgramsList(programsList)
                    Timber.d("submitProgramsList")
                    Timber.d("getRandomProgramIdByGroupId accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomProgramIdByGroupId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    override fun getRandomDayId() {
        view?.showLoading()
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
                    view?.submitDaysList(daysList)
                    Timber.d("submitDaysList")
                    Timber.d("getRandomDayId accomplished")
                    view?.hideLoading()
                }, {
                    it.printStackTrace()
                    Timber.d("getRandomDayId error: ${it.message}")
                    view?.hideLoading()
                })
        )
    }

    //endregion

    //region ====================== 4.2. load data from DB and submit it adapters ======================

    override fun loadGroupsFromDb() {
        view?.showLoading()
        subscribe(
            getAllGroupsFromDbUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupsList ->
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

    override fun loadDaysFromDb(selectedDayId: String) {
        view?.showLoading()
        subscribe(
            getDaysFromDbFromDbUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ daysList ->
                    view?.submitDaysList(daysList)
                    Timber.d("submitDaysList")
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

    //endregion

    //region ====================== remove data ======================

    override fun removeAllGroups() {
        view?.showLoading()
        subscribe(
            removeAllGroupsUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    Timber.d("removeAllGroups accomplished")
                    view?.hideLoading()
                }, {
                    Timber.d("removeAllGroups error")
                    view?.hideLoading()
                })
        )
    }

    //endregion

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

    //region ====================== select views ======================

    override fun setSelectedGroupView(
        previousPosition: Pair<Int, GroupEntity?>,
        selectedPosition: Pair<Int, GroupEntity?>,
        currentList: List<GroupEntity>
    ) {
        val newList: MutableList<GroupEntity> = ArrayList(currentList)
        //  replace
        selectedPosition.second?.let {
            newList.removeAt(selectedPosition.first)
            newList.add(selectedPosition.first, it)
        }
        previousPosition.second?.let {
            newList.removeAt(previousPosition.first)
            newList.add(previousPosition.first, it)
        }
        //  delete previous list in adapter and insert new one
        view?.submitGroupsList(null)
        view?.submitGroupsList(newList)
    }

    override fun setSelectedChannelView(
        previousPosition: Pair<Int, ChannelEntity?>,
        selectedPosition: Pair<Int, ChannelEntity?>,
        currentList: List<ChannelEntity>
    ) {
        val newList: MutableList<ChannelEntity> = ArrayList(currentList)
        //  replace
        selectedPosition.second?.let {
            newList.removeAt(selectedPosition.first)
            newList.add(selectedPosition.first, it)
        }
        previousPosition.second?.let {
            newList.removeAt(previousPosition.first)
            newList.add(previousPosition.first, it)
        }
        //  delete previous list in adapter and insert new one
        view?.submitChannelsList(null)
        view?.submitChannelsList(newList)
    }

    override fun setSelectedProgramView(
        previousPosition: Pair<Int, ProgramEntity?>,
        selectedPosition: Pair<Int, ProgramEntity?>,
        currentList: List<ProgramEntity>
    ) {
        val newList: MutableList<ProgramEntity> = ArrayList(currentList)
        //  replace
        selectedPosition.second?.let {
            newList.removeAt(selectedPosition.first)
            newList.add(selectedPosition.first, it)
        }
        previousPosition.second?.let {
            newList.removeAt(previousPosition.first)
            newList.add(previousPosition.first, it)
        }
        //  delete previous list in adapter and insert new one
        view?.submitProgramsList(null)
        view?.submitProgramsList(newList)
    }

    override fun setSelectedDayView(
        previousPosition: Pair<Int, DayEntity?>,
        selectedPosition: Pair<Int, DayEntity?>,
        currentList: List<DayEntity>
    ) {
        val newList: MutableList<DayEntity> = ArrayList(currentList)
        //  replace
        selectedPosition.second?.let {
            newList.removeAt(selectedPosition.first)
            newList.add(selectedPosition.first, it)
        }
        previousPosition.second?.let {
            newList.removeAt(previousPosition.first)
            newList.add(previousPosition.first, it)
        }
        //  delete previous list in adapter and insert new one
        view?.submitDaysList(null)
        view?.submitDaysList(newList)
    }

    //endregion
}