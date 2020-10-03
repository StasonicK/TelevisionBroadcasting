package com.eburg_soft.televisionbroadcasting.data.datasource.database

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

object TestDataDb {

    //  TEST ENTITIES
    //  test group entities
    val TEST_GROUP_ENTITY_1 = GroupEntity("group_1", "group_name_1")
    val TEST_GROUP_ENTITY_2 = GroupEntity("group_2", "group_name_2")
    val TEST_GROUP_ENTITY_3 = GroupEntity("group_3", "group_name_3")
    val TEST_GROUP_ENTITY_4 = GroupEntity("group_4", "group_name_4")
    val TEST_GROUP_ENTITY_5 = GroupEntity("group_5", "group_name_5")
    val TEST_GROUP_ENTITIES = arrayListOf(
        TEST_GROUP_ENTITY_1,
        TEST_GROUP_ENTITY_2,
        TEST_GROUP_ENTITY_3,
        TEST_GROUP_ENTITY_4,
        TEST_GROUP_ENTITY_5
    )

    //  test channel entities
    //  for channel_1:
    val TEST_CHANNEL_ENTITY_1_1 = ChannelEntity("channel_101", "group_1", "channel_name_101", "logo_url_101")

    //  for channel_2:
    val TEST_CHANNEL_ENTITY_2_1 = ChannelEntity("channel_201", "group_2", "channel_name_201", "logo_url_201")
    val TEST_CHANNEL_ENTITY_2_2 = ChannelEntity("channel_202", "group_2", "channel_name_202", "logo_url_202")
    val TEST_CHANNEL_ENTITY_2_3 = ChannelEntity("channel_203", "group_2", "channel_name_203", "logo_url_203")
    val TEST_CHANNEL_ENTITIES_2 =
        arrayListOf(TEST_CHANNEL_ENTITY_2_1, TEST_CHANNEL_ENTITY_2_2, TEST_CHANNEL_ENTITY_2_3)

    //  for channel_3:
    val TEST_CHANNEL_ENTITY_3_1 = ChannelEntity("channel_301", "group_3", "channel_name_301", "logo_url_301")
    val TEST_CHANNEL_ENTITY_3_2 = ChannelEntity("channel_302", "group_3", "channel_name_302", "logo_url_302")
    val TEST_CHANNEL_ENTITY_3_3 = ChannelEntity("channel_303", "group_3", "channel_name_303", "logo_url_303")
    val TEST_CHANNEL_ENTITY_3_4 = ChannelEntity("channel_304", "group_3", "channel_name_304", "logo_url_304")
    val TEST_CHANNEL_ENTITY_3_5 = ChannelEntity("channel_305", "group_3", "channel_name_305", "logo_url_305")
    val TEST_CHANNEL_ENTITIES_3 = arrayListOf(
        TEST_CHANNEL_ENTITY_3_1,
        TEST_CHANNEL_ENTITY_3_2,
        TEST_CHANNEL_ENTITY_3_3,
        TEST_CHANNEL_ENTITY_3_4,
        TEST_CHANNEL_ENTITY_3_5
    )

    //  for channel_4:
    val TEST_CHANNEL_ENTITY_4_1 = ChannelEntity("channel_401", "group_4", "channel_name_401", "logo_url_401")
    val TEST_CHANNEL_ENTITY_4_2 = ChannelEntity("channel_402", "group_4", "channel_name_402", "logo_url_402")
    val TEST_CHANNEL_ENTITY_4_3 = ChannelEntity("channel_403", "group_4", "channel_name_403", "logo_url_403")
    val TEST_CHANNEL_ENTITY_4_4 = ChannelEntity("channel_404", "group_4", "channel_name_404", "logo_url_404")
    val TEST_CHANNEL_ENTITY_4_5 = ChannelEntity("channel_405", "group_4", "channel_name_405", "logo_url_405")
    val TEST_CHANNEL_ENTITY_4_6 = ChannelEntity("channel_406", "group_4", "channel_name_406", "logo_url_406")
    val TEST_CHANNEL_ENTITY_4_7 = ChannelEntity("channel_407", "group_4", "channel_name_407", "logo_url_407")
    val TEST_CHANNEL_ENTITIES_4 = arrayListOf(
        TEST_CHANNEL_ENTITY_4_1,
        TEST_CHANNEL_ENTITY_4_2,
        TEST_CHANNEL_ENTITY_4_3,
        TEST_CHANNEL_ENTITY_4_4,
        TEST_CHANNEL_ENTITY_4_5,
        TEST_CHANNEL_ENTITY_4_6,
        TEST_CHANNEL_ENTITY_4_7
    )

    //  for channel_5:
    val TEST_CHANNEL_ENTITY_5_1 = ChannelEntity("channel_501", "group_5", "channel_name_501", "logo_url_501")
    val TEST_CHANNEL_ENTITY_5_2 = ChannelEntity("channel_502", "group_5", "channel_name_502", "logo_url_502")
    val TEST_CHANNEL_ENTITY_5_3 = ChannelEntity("channel_503", "group_5", "channel_name_503", "logo_url_503")
    val TEST_CHANNEL_ENTITY_5_4 = ChannelEntity("channel_504", "group_5", "channel_name_504", "logo_url_504")
    val TEST_CHANNEL_ENTITY_5_5 = ChannelEntity("channel_505", "group_5", "channel_name_505", "logo_url_505")
    val TEST_CHANNEL_ENTITY_5_6 = ChannelEntity("channel_506", "group_5", "channel_name_506", "logo_url_506")
    val TEST_CHANNEL_ENTITY_5_7 = ChannelEntity("channel_507", "group_5", "channel_name_507", "logo_url_507")
    val TEST_CHANNEL_ENTITY_5_8 = ChannelEntity("channel_508", "group_5", "channel_name_508", "logo_url_508")
    val TEST_CHANNEL_ENTITY_5_9 = ChannelEntity("channel_509", "group_5", "channel_name_509", "logo_url_509")
    val TEST_CHANNEL_ENTITIES_5 = arrayListOf(
        TEST_CHANNEL_ENTITY_5_1,
        TEST_CHANNEL_ENTITY_5_2,
        TEST_CHANNEL_ENTITY_5_3,
        TEST_CHANNEL_ENTITY_5_4,
        TEST_CHANNEL_ENTITY_5_5,
        TEST_CHANNEL_ENTITY_5_6,
        TEST_CHANNEL_ENTITY_5_7,
        TEST_CHANNEL_ENTITY_5_8,
        TEST_CHANNEL_ENTITY_5_9
    )

    //  test program entities
    fun generateTestProgramEntities(idNumber: Int, count: Int, channelId: String): List<ProgramEntity> {
        val list = mutableListOf<ProgramEntity>()
        for (i in 0 until count) {
            list.add(ProgramEntity("program-$idNumber-$i", channelId, "Передача $idNumber-$i"))
        }
        return list
    }

    //  test day entities
    fun generateDayEntities(startDate: String, endDate: String): List<DayEntity> {
        var id = 0
        val pattern = "dd.MM.yyyy"
        val patternToString = "dd MMMM yyyy"
        val dateFormat = SimpleDateFormat(pattern)

        val start: Date? = dateFormat.parse(startDate)
        val end: Date? = dateFormat.parse(endDate)
        val calendar = GregorianCalendar();
        calendar.time = start;

        val totalDates: MutableList<DayEntity> = ArrayList()

        while (calendar.time.before(end)) {
            val date = SimpleDateFormat(patternToString).format(calendar.time)
            totalDates.add(DayEntity(id.toString(), date))

            calendar.add(Calendar.DATE, 1)
            id += 1
        }
        return totalDates
    }
}