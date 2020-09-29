package com.eburg_soft.televisionbroadcasting.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.eburg_soft.televisionbroadcasting.data.datasource.database.TVDatabase
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import org.junit.*

abstract class TVDatabaseTest {

    private lateinit var tvDatabase: TVDatabase

    fun getGroupDao(): GroupDao? = tvDatabase.groupDao()

    fun getChannelDao(): ChannelDao? = tvDatabase.channelDao()

    fun getProgramDao(): ProgramDao? = tvDatabase.programDao()

    @Before
    fun init() {
        tvDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TVDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

//        RxAndroidPlugins.reset()
//        RxJavaPlugins.reset()
//
//        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
//            Schedulers.trampoline()
//        }
    }

    @After
    fun finish() {
        tvDatabase.close()
    }
}