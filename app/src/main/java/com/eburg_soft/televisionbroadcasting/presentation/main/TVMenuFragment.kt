package com.eburg_soft.televisionbroadcasting.presentation.main

class TVMenuFragment
//    :     Fragment(R.layout.fragment_tv_menu), TVMenuContract.View
//    , GroupsScrollListener.GroupsCallback,
//    ChannelsScrollListener.ChannelCallback, ProgramsScrollListener.ProgramCallback, DaysScrollListener.DaysCallback,
//    GroupController.GroupCallback, ChannelController.ChannelCallback, ProgramController.ProgramCallback,
//    DayController.DayCallback
{
//
//    @Inject
//    lateinit var presenter: TVMenuContract.Presenter
//
////    private val groupController by lazy {
////        GroupController(this)
////    }
////
////    private val channelController by lazy {
////        ChannelController(this)
////    }
////
////    private val programController by lazy {
////        ProgramController(this)
////    }
////
////    private val dayController by lazy {
////        DayController(this)
////    }
//
//    //    private val groupsAdapter = GroupsAdapter()
////    private val channelsAdapter = ChannelsAdapter()
////    private val programsAdapter = ProgramsAdapter()
////    private val daysAdapter = DaysAdapter()
//    private val groupsAdapter = GroupsSnappyAdapter()
//    private val channelsAdapter = ChannelsSnappyAdapter()
//    private val programsAdapter = ProgramsSnappyAdapter()
//    private val daysAdapter = DaysSnappyAdapter()
//
//    private var selectedGroupId: String? = null
//    private var selectedChannelId: String? = null
//    private var selectedProgramId: String? = null
//    private var selectedDayId: String? = null
//
//    private var groupRecyclerTouchStatus: Boolean = false
//    private var channelRecyclerTouchStatus: Boolean = false
//    private var programRecyclerTouchStatus: Boolean = false
//    private var dayRecyclerTouchStatus: Boolean = false
//
//    private var selectedGroupItemPosition = -1
//    private var previousGroupItemPosition = -1
//    private var selectedChannelItemPosition = -1
//    private var previousChannelItemPosition = -1
//    private var selectedProgramItemPosition = -1
//    private var previousProgramItemPosition = -1
//    private var selectedDayItemPosition = -1
//    private var previousDayItemPosition = -1
//
//    companion object {
//
//        const val GROUP_ID = "group id"
//        const val CHANNEL_ID = "channel id"
//        const val PROGRAM_ID = "program id"
//        const val DAY_ID = "day id"
//
//        const val GROUP_RECYCLER_TOUCH_STATUS = "group recycler touch status"
//        const val CHANNEL_RECYCLER_TOUCH_STATUS = "channel recycler touch status"
//        const val PROGRAM_RECYCLER_TOUCH_STATUS = "program recycler touch status"
//        const val DAY_RECYCLER_TOUCH_STATUS = "day recycler touch status"
//
//        const val SELECTED_GROUP_ITEM_POSITION = "selected group item position"
//        const val PREVIOUS_GROUP_ITEM_POSITION = "previous group item position"
//        const val SELECTED_CHANNEL_ITEM_POSITION = "selected channel item position"
//        const val PREVIOUS_CHANNEL_ITEM_POSITION = "previous channel item position"
//        const val SELECTED_PROGRAM_ITEM_POSITION = "selected program item position"
//        const val PREVIOUS_PROGRAM_ITEM_POSITION = "previous program item position"
//        const val SELECTED_DAY_ITEM_POSITION = "selected day item position"
//        const val PREVIOUS_DAY_ITEM_POSITION = "previous day item position"
//
//        @JvmStatic
//        fun getNewInstance(): TVMenuFragment = TVMenuFragment()
//    }
//
//    private fun getTVMenuComponent(context: Context): TVMenuComponent =
//        (context.applicationContext as TelevisionBroadcastingApp)
//            .component
//            .createTVMenuComponent(TVMenuContextModule(context))
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        getTVMenuComponent(requireContext()).inject(this)
//        presenter.attach(this)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initGroupsRecycler()
//        initChannelsRecycler()
//        initProgramsRecycler()
//        initDaysRecycler()
//
//        savedInstanceState?.let {
//            //  get ids
//            selectedGroupId = it.getString(GROUP_ID)
//            selectedChannelId = it.getString(CHANNEL_ID)
//            selectedProgramId = it.getString(PROGRAM_ID)
//            selectedDayId = it.getString(DAY_ID)
//
//            //  get touch statuses
//            groupRecyclerTouchStatus = it.getBoolean(GROUP_RECYCLER_TOUCH_STATUS)
//            channelRecyclerTouchStatus = it.getBoolean(CHANNEL_RECYCLER_TOUCH_STATUS)
//            programRecyclerTouchStatus = it.getBoolean(PROGRAM_RECYCLER_TOUCH_STATUS)
//            dayRecyclerTouchStatus = it.getBoolean(DAY_RECYCLER_TOUCH_STATUS)
//
//            //  get selected item positions
//            selectedGroupItemPosition = it.getInt(SELECTED_GROUP_ITEM_POSITION)
//            selectedChannelItemPosition = it.getInt(SELECTED_CHANNEL_ITEM_POSITION)
//            selectedProgramItemPosition = it.getInt(SELECTED_PROGRAM_ITEM_POSITION)
//            selectedDayItemPosition = it.getInt(SELECTED_DAY_ITEM_POSITION)
//
//            //  get previous item positions
//            previousGroupItemPosition = it.getInt(PREVIOUS_GROUP_ITEM_POSITION)
//            previousChannelItemPosition = it.getInt(PREVIOUS_CHANNEL_ITEM_POSITION)
//            previousProgramItemPosition = it.getInt(PREVIOUS_PROGRAM_ITEM_POSITION)
//            previousDayItemPosition = it.getInt(PREVIOUS_DAY_ITEM_POSITION)
//        }
//
//
//        if (savedInstanceState == null) {
//            presenter.syncData()
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        //  save ids
//        outState.putString(GROUP_ID, selectedGroupId)
//        outState.putString(CHANNEL_ID, selectedProgramId)
//        outState.putString(PROGRAM_ID, selectedProgramId)
//        outState.putString(DAY_ID, selectedDayId)
//
//        //  save touch statuses
//        outState.putBoolean(GROUP_RECYCLER_TOUCH_STATUS, groupRecyclerTouchStatus)
//        outState.putBoolean(CHANNEL_RECYCLER_TOUCH_STATUS, channelRecyclerTouchStatus)
//        outState.putBoolean(PROGRAM_RECYCLER_TOUCH_STATUS, programRecyclerTouchStatus)
//        outState.putBoolean(DAY_RECYCLER_TOUCH_STATUS, dayRecyclerTouchStatus)
//
//        //  save selected item positions
//        outState.putInt(SELECTED_GROUP_ITEM_POSITION, selectedGroupItemPosition)
//        outState.putInt(SELECTED_CHANNEL_ITEM_POSITION, selectedChannelItemPosition)
//        outState.putInt(SELECTED_PROGRAM_ITEM_POSITION, selectedProgramItemPosition)
//        outState.putInt(SELECTED_DAY_ITEM_POSITION, selectedDayItemPosition)
//
//        //  save previous item positions
//        outState.putInt(PREVIOUS_GROUP_ITEM_POSITION, previousGroupItemPosition)
//        outState.putInt(PREVIOUS_CHANNEL_ITEM_POSITION, previousChannelItemPosition)
//        outState.putInt(PREVIOUS_PROGRAM_ITEM_POSITION, previousProgramItemPosition)
//        outState.putInt(PREVIOUS_DAY_ITEM_POSITION, previousDayItemPosition)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        presenter.detach()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        presenter.removeAllGroups()
//    }
//
//    //region ====================== Contract implementation ======================
//
//    override fun showLoading() {
//        pb_tv_menu.visibility = View.VISIBLE
//        Timber.d("showLoading")
//    }
//
//    override fun hideLoading() {
//        pb_tv_menu.visibility = View.GONE
//        Timber.d("hideLoading")
//    }
//
//    override fun submitGroupsList(list: List<GroupEntity>?) {
////        groupsAdapter.setData(list)
////        groupsAdapter.submitList(list)
////        groupController.setData(list)
//
//        groupsAdapter.setData(list)
////        groupsAdapter.notifyDataSetChanged()
//        Timber.d("submitGroupList")
//    }
//
//    override fun submitChannelsList(list: List<ChannelEntity>?) {
////        channelsAdapter.setData(list)
////        channelsAdapter.submitList(list)
////        channelController.setData(list)
//        channelsAdapter.setData(list)
//        Timber.d("submitChannelList")
//    }
//
//    override fun submitProgramsList(list: List<ProgramEntity>?) {
////        programsAdapter.setData(list)
////        programsAdapter.submitList(list)
////        programController.setData(list)
//        programsAdapter.setData(list)
//        Timber.d("submitProgramList")
//    }
//
//    override fun submitDaysList(list: List<DayEntity>?) {
////        daysAdapter.setData(list)
////        daysAdapter.submitList(list)
////        dayController.setData(list)
//        daysAdapter.setData(list)
//        Timber.d("submitDaysList")
//    }
//
//    override fun saveSelectedGroupId(groupId: String) {
//        selectedGroupId = groupId
//        Timber.d("submitDefaultGroupId: $selectedGroupId")
//        showNetworkErrorMessage("groupId: $selectedGroupId")
//    }
//
//    override fun saveSelectedChannelId(channelId: String) {
//        selectedChannelId = channelId
//        Timber.d("submitDefaultChannelId: $selectedChannelId")
//        showNetworkErrorMessage("channelId: $selectedChannelId")
//    }
//
//    override fun saveSelectedProgramId(programId: String) {
//        selectedProgramId = programId
//        Timber.d("submitDefaultChannelId: $selectedProgramId")
//        showNetworkErrorMessage("channelId: $selectedProgramId")
//    }
//
//    override fun saveSelectedDayId(dayId: String) {
//        selectedDayId = dayId
//        Timber.d("submitDefaultChannelId: $selectedDayId")
//        showNetworkErrorMessage("channelId: $selectedDayId")
//    }
//
//    override fun showNetworkErrorMessage(message: String) {
//        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
//        Timber.d(message)
//    }
//
//    override fun initGroupsRecycler() {
//        recycler_groups_list.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            val itemSize =
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, resources.displayMetrics).toInt()
//            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
//            setCenteringPadding(itemSize, margin, 0)
//            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
//            groupsAdapter.setCallback(object : SnappyAdapter.Callback {
//                override fun onItemCentered(position: Int) {
//                    val groupId = groupsAdapter.getItemAt(position).id
//                    presenter.loadChannelsByGroupIdFromDb(groupId)
//                }
//            }
//            )
//            adapter = groupsAdapter
//        }
//
////        recycler_groups_list.apply {
////            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setHasFixedSize(true)
////            val itemSize =
////                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, resources.displayMetrics).toInt()
////            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
////            setCenteringPadding(itemSize, margin, 0)
////            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
////            groupsAdapter.setCallback(object : SnappyAdapter.Callback {
////                override fun onItemCentered(position: Int) {
////                    val groupId = groupsAdapter.getItemAt(position).id
////                    presenter.loadChannelsByGroupIdFromDb(groupId)
////                }
////            }
////            )
////            adapter = groupsAdapter
////        }
//
////        val snapHelper = LinearSnapHelper()
////        recycler_groups_list.apply {
////            val layoutManager = CenterLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setLayoutManager(layoutManager)
////            setController(groupController)
////            addItemDecoration(
////                ItemDecoration(
////                    resources.getDimension(R.dimen.width_group_item).toInt(),
////                    resources.getDimension(R.dimen.padding_normal).toInt()
//////                    0
////                )
////            )
//////            addItemDecoration(ItemDecoration())
////            addOnScrollListener(
////                GroupsScrollListener(
////                    snapHelper,
////                    this@TVMenuFragment
////                )
////            )
////        }
////        // LinearSnapHelper will try to locate at center when scroll
////        snapHelper.attachToRecyclerView(recycler_groups_list)
//
////        recycler_groups_list.apply {
////            groupsAdapter.apply {
////                setOnClick { item0, positionItem0, item1, positionItem1 ->
////                    (item1 as GroupEntity).let {
////                        presenter.loadChannelsByGroupIdFromDb(it.id)
////                        selectedGroupId = it.id
//////                        showNetworkErrorMessage("Clicked on $selectedGroupId")
////                    }
////                    (item0 as GroupEntity?).let {
////                    }
////                    previousGroupItemPosition = positionItem0
////                    selectedGroupItemPosition = positionItem1
////                    presenter.setSelectedGroupView(
////                        previousGroupItemPosition to item0,
////                        selectedGroupItemPosition to item1,
////                        currentList
////                    )
////                }
//////                presenter.setSelectedGroupView(
//////                    previousGroupItemPosition to currentList[previousGroupItemPosition],
//////                    selectedGroupItemPosition to currentList[selectedGroupItemPosition],
//////                    currentList
//////                )
////                setOnTouch {
////                    groupRecyclerTouchStatus = it
//////                    if (groupRecyclerTouchStatus) {
//////                        changeBackgroundColor(R.color.white_transparent)
//////                        elevateOnTouch()
//////                    } else {
//////                        changeBackgroundColor(R.color.black)
//////                        elevateBackOutOfTouch()
//////                    }
////                    Timber.d("Group $it touched")
////                }
////            }
////            adapter = groupsAdapter
////            setHasFixedSize(true)
////        }
//        //This is used to center first and last item on screen
////        recycler_groups_list.centerListInLinearLayout(R.dimen.width_group_item)
////        recycler_groups_list.apply {
////            addItemDecoration(CustomItemDecoration())
////            setOnCenterItemChangedListener(this)
////        }
//
//        // Attach OnScrollListener to your RecyclerView
////        recycler_groups_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
////            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
////                recyclerView.post {
////                    recyclerView.selectMiddleItem(recyclerView, requireContext(), groupsAdapter)
////                }
////            }
////        })
//
//        Timber.d("initGroupsRecycler accomplished")
//    }
//
//    override fun initChannelsRecycler() {
//
//        recycler_channels_list.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            val itemSize =
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, resources.displayMetrics).toInt()
//            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
//            setCenteringPadding(itemSize, margin, 0)
//            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
//            channelsAdapter.setCallback(object : SnappyAdapter.Callback {
//                override fun onItemCentered(position: Int) {
//                    val channelId = channelsAdapter.getItemAt(position).id
//                    presenter.loadProgramsByChannelIdFromDb(channelId)
//                }
//            }
//            )
//            adapter = channelsAdapter
//        }
//
////        recycler_channels_list.apply {
////            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setHasFixedSize(true)
////            val itemSize =
////                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, resources.displayMetrics).toInt()
////            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
////            setCenteringPadding(itemSize, margin, 0)
////            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
////            channelsAdapter.setCallback(object : SnappyAdapter.Callback {
////                override fun onItemCentered(position: Int) {
////                    val channelId = channelsAdapter.getItemAt(position).id
////                    presenter.loadProgramsByChannelIdFromDb(channelId)
////                }
////            }
////            )
////            adapter = channelsAdapter
////        }
//
////        val snapHelper = LinearSnapHelper()
////        recycler_channels_list.apply {
////            val layoutManager = CenterLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setLayoutManager(layoutManager)
////            setController(channelController)
////            addItemDecoration(
////                ItemDecoration(
////                    resources.getDimension(R.dimen.width_channel_item).toInt(),
////                    resources.getDimension(R.dimen.padding_normal).toInt()
////                )
////            )
//////            addItemDecoration(ItemDecoration())
////            addOnScrollListener(
////                ChannelsScrollListener(
////                    snapHelper,
////                    this@TVMenuFragment
////                )
////            )
////        }
////        // LinearSnapHelper will try to locate at center when scroll
////        snapHelper.attachToRecyclerView(recycler_channels_list)
//
////        recycler_channels_list.apply {
////            channelsAdapter.apply {
////                setOnClick { item0, positionItem0, item1, positionItem1 ->
////                    (item1 as ChannelEntity).let {
////                        presenter.loadProgramsByChannelIdFromDb(it.id)
////                        selectedChannelId = it.id
//////                        showNetworkErrorMessage("Clicked on $selectedChannelId")
////                    }
////                    (item0 as ChannelEntity?).let {
////                    }
////                    previousChannelItemPosition = positionItem0
////                    selectedChannelItemPosition = positionItem1
////                    presenter.setSelectedChannelView(
////                        previousChannelItemPosition to item0,
////                        selectedChannelItemPosition to item1,
////                        currentList
////                    )
//////                    presenter.setSelectedChannelView(
//////                        previousChannelItemPosition to currentList[previousChannelItemPosition],
//////                        selectedChannelItemPosition to currentList[selectedChannelItemPosition],
//////                        currentList
//////                    )
////                    setOnTouch {
////                        channelRecyclerTouchStatus = it
////                        Timber.d("Channel $it touched")
////                    }
////                }
////            }
////            channelRecyclerTouchStatus.apply {
//////                if (channelRecyclerTouchStatus) {
//////                    changeBackgroundColor(R.color.white_transparent)
//////                    elevate(true)
//////                } else {
//////                    changeBackgroundColor(R.color.black)
//////                    elevate(false)
//////                }
////            }
////            adapter = channelsAdapter
////            setHasFixedSize(true)
////        }
////        //This is used to center first and last item on screen
//////        recycler_channel_list.centerListInLinearLayout(R.dimen.width_channel_item)
//////        recycler_channels_list.addItemDecoration(CustomItemDecoration())
//        Timber.d("initChannelsRecycler accomplished")
//    }
//
//    override fun initProgramsRecycler() {
//
//        recycler_programs_list.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            val itemSize =
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 168f, resources.displayMetrics).toInt()
//            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
//            setCenteringPadding(itemSize, margin, 0)
//            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
//            adapter = programsAdapter
//        }
////        recycler_programs_list.apply {
////            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setHasFixedSize(true)
////            val itemSize =
////                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 168f, resources.displayMetrics).toInt()
////            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
////            setCenteringPadding(itemSize, margin, 0)
////            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
////            adapter = programsAdapter
////        }
//
////        val snapHelper = LinearSnapHelper()
////        recycler_programs_list
////        val layoutManager = CenterLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////        recycler_programs_list.layoutManager = layoutManager
////        recycler_programs_list.setController(programController)
////        recycler_programs_list.addItemDecoration(
////            ItemDecoration(
////                resources.getDimension(R.dimen.width_program_item).toInt(),
////                resources.getDimension(R.dimen.padding_small).toInt()
////            )
////        )
//////            recycler_programs_list.addItemDecoration(ItemDecoration())
////        recycler_programs_list.addOnScrollListener(
////            ProgramsScrollListener(
////                snapHelper,
////                this@TVMenuFragment
////            )
////        )
////
////        // LinearSnapHelper will try to locate at center when scroll
////        snapHelper.attachToRecyclerView(recycler_programs_list)
//
////        recycler_programs_list.apply {
////            programsAdapter.apply {
////                setOnClick { item0, positionItem0, item1, positionItem1 ->
////                    (item1 as ProgramEntity).let {
////                        selectedProgramId = it.id
//////                        showNetworkErrorMessage("Clicked on $selectedProgramId")
////                    }
////                    (item0 as ProgramEntity?).let {
////                    }
////                    previousProgramItemPosition = positionItem0
////                    selectedProgramItemPosition = positionItem1
////                    presenter.setSelectedProgramView(
////                        previousProgramItemPosition to item0,
////                        selectedProgramItemPosition to item1,
////                        currentList
////                    )
////                }
//////                presenter.setSelectedProgramView(
//////                    previousProgramItemPosition to currentList[previousProgramItemPosition],
//////                    selectedProgramItemPosition to currentList[selectedProgramItemPosition],
//////                    currentList
//////                )
////                setOnTouch {
////                    programRecyclerTouchStatus = it
////                    Timber.d("Program $it touched")
////                }
////            }
////            programRecyclerTouchStatus.apply {
//////                if (channelRecyclerTouchStatus) {
//////                    changeBackgroundColor(R.color.white_transparent)
//////                    elevate(true)
//////                } else {
//////                    changeBackgroundColor(R.color.black)
//////                    elevate(false)
//////                }
////            }
////            adapter = programsAdapter
////            setHasFixedSize(true)
////        }
////        //This is used to center first and last item on screen
//////        recycler_programs_list.centerListInLinearLayout(R.dimen.width_program_item)
//////        recycler_programs_list.addItemDecoration(CustomItemDecoration())
//
//        Timber.d("initProgramsRecycler accomplished")
//    }
//
//    override fun initDaysRecycler() {
//
//        recycler_days_list.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            val itemSize =
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, resources.displayMetrics).toInt()
//            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
//            setCenteringPadding(itemSize, margin, 0)
//            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
//            adapter = daysAdapter
//        }
//
////        recycler_days_list.apply {
////            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setHasFixedSize(true)
////            val itemSize =
////                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, resources.displayMetrics).toInt()
////            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
////            setCenteringPadding(itemSize, margin, 0)
////            enableSnapListener(SnappyRecyclerView.Behavior.NOTIFY_ON_IDLE)
////            adapter = daysAdapter
////        }
//
////        val snapHelper = LinearSnapHelper()
////        recycler_days_list.apply {
////            val layoutManager = CenterLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
////            setLayoutManager(layoutManager)
////            setController(dayController)
////            addItemDecoration(
////                ItemDecoration(
////                    resources.getDimension(R.dimen.width_day_item).toInt(),
////                    resources.getDimension(R.dimen.padding_normal).toInt()
////                )
////            )
//////            addItemDecoration(ItemDecoration())
////            addOnScrollListener(
////                DaysScrollListener(
////                    snapHelper,
////                    this@TVMenuFragment
////                )
////            )
////        }
////        // LinearSnapHelper will try to locate at center when scroll
////        snapHelper.attachToRecyclerView(recycler_days_list)
//
////        recycler_days_list.apply {
////            daysAdapter.apply {
////                setOnClick { item0, positionItem0, item1, positionItem1 ->
////                    (item1 as DayEntity).let {
////                        selectedDayId = it.id
////                        showNetworkErrorMessage("Clicked on $selectedDayId")
////                    }
////                    (item0 as DayEntity?).let {
////                    }
////                    previousDayItemPosition = positionItem0
////                    selectedDayItemPosition = positionItem1
////                    presenter.setSelectedDayView(
////                        previousDayItemPosition to item0,
////                        selectedDayItemPosition to item1,
////                        currentList
////                    )
////                }
//////                presenter.setSelectedDayView(
//////                    previousDayItemPosition to currentList[previousDayItemPosition],
//////                    selectedDayItemPosition to currentList[selectedDayItemPosition],
//////                    currentList
//////                )
////                setOnTouch {
////                    dayRecyclerTouchStatus = it
////                    Timber.d("Day $it touched")
////                }
////            }
////            dayRecyclerTouchStatus.apply {
//////                if (dayRecyclerTouchStatus) {
//////                    changeBackgroundColor(R.color.white_transparent)
//////                    elevate(true)
//////                } else {
//////                    changeBackgroundColor(R.color.black)
//////                    elevate(false)
//////                }
////            }
////            adapter = daysAdapter
////            setHasFixedSize(true)
////        }
////        //This is used to center first and last item on screen
//////        recycler_days_list.centerListInLinearLayout(R.dimen.width_day_item)
//////        recycler_days_list.addItemDecoration(CustomItemDecoration())
//
//        Timber.d("initDaysRecycler accomplished")
//    }
//
//    override fun populateGroupsRecycler() {
//        presenter.verifySelectedIdAndLoadGroupsFromDb(selectedGroupId)
//        Timber.d("populateGroupsRecycler accomplished")
//    }
//
//    override fun populateChannelsRecycler() {
//        presenter.verifySelectedIdAndLoadChannelsFromDb(selectedGroupId!!, selectedChannelId)
//        Timber.d("populateChannelsRecycler accomplished")
//    }
//
//    override fun populateProgramsRecycler() {
//        presenter.verifySelectedIdAndLoadProgramsFromDb(selectedChannelId!!, selectedProgramId)
//        Timber.d("populateProgramsRecycler accomplished")
//    }
//
//    override fun populateDaysRecycler() {
//        presenter.verifySelectedIdAndLoadDaysFromDb(selectedDayId)
//        Timber.d("populateDaysRecycler accomplished")
//    }
//
////    override fun onGroupClick(groupEntity: GroupEntity, position: Int) {
////        Timber.d("onGroupClick $position")
////        recycler_groups_list.smoothScrollToPosition(position)
////        presenter.loadChannelsByGroupIdFromDb(groupEntity.id)
////    }
////
////    override fun onChannelClick(channelEntity: ChannelEntity, position: Int) {
////        Timber.d("onChannelClick $position")
////        recycler_channels_list.smoothScrollToPosition(position)
////        presenter.loadProgramsByChannelIdFromDb(channelEntity.id)
////    }
////
////    override fun onProgramClick(programEntity: ProgramEntity, position: Int) {
////        Timber.d("onProgramClick $position")
////        recycler_programs_list.smoothScrollToPosition(position)
////    }
////
////    override fun onDayClick(dayEntity: DayEntity, position: Int) {
////        Timber.d("onDayClick $position")
////        recycler_days_list.smoothScrollToPosition(position)
////    }
////
////    override fun onGroupsPositionChanged(position: Int) {
////    }
////
////    override fun onChannelsPositionChanged(position: Int) {
////    }
////
////    override fun onProgramsPositionChanged(position: Int) {
////    }
////
////    override fun onDaysPositionChanged(position: Int) {
////    }
//
//    //endregion
//
}