package eburg_soft.televisionbroadcasting.utils

import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ChannelResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

object TestUtil {

    //  TEST RESPONSES
    //  test group and channel responses
    val TEST_GROUP_RESPONSE_1 =
        GroupResponse(
            "group_id_1",
            "group_name_1",
            listOf(
                ChannelResponse("channel-id-1-1", "channel_name_1_1", "logo_url_11")
            )
        )
    val TEST_GROUP_RESPONSE_2 = GroupResponse(
        "group_id_2",
        "group_name_2",
        listOf(
            ChannelResponse("channel-id-2-1", "channel_name_2_1", "logo_url_21"),
            ChannelResponse("channel-id-2-2", "channel_name_2_2", "logo_url_22"),
            ChannelResponse("channel-id-2-3", "channel_name_2_3", "logo_url_23")
        )
    )
    val TEST_GROUP_RESPONSE_3 =
        GroupResponse(
            "group_id_3",
            "group_name_3",
            listOf(
                ChannelResponse("channel-id-3-1", "channel_name_3_1", "logo_url_31"),
                ChannelResponse("channel-id-3-2", "channel_name_3_2", "logo_url_32"),
                ChannelResponse("channel-id-3-3", "channel_name_3_3", "logo_url_33"),
                ChannelResponse("channel-id-3-4", "channel_name_3_4", "logo_url_34"),
                ChannelResponse("channel-id-3-5", "channel_name_3_5", "logo_url_35")
            )
        )
    val TEST_GROUP_RESPONSE_4 =
        GroupResponse(
            "group_id_4",
            "group_name_4",
            listOf(
                ChannelResponse("channel-id-4-1", "channel_name_4_1", "logo_url_41"),
                ChannelResponse("channel-id-4-2", "channel_name_4_2", "logo_url_42"),
                ChannelResponse("channel-id-4-3", "channel_name_4_3", "logo_url_43"),
                ChannelResponse("channel-id-4-4", "channel_name_4_4", "logo_url_44"),
                ChannelResponse("channel-id-4-5", "channel_name_4_5", "logo_url_45"),
                ChannelResponse("channel-id-4-6", "channel_name_4_6", "logo_url_46"),
                ChannelResponse("channel-id-4-7", "channel_name_4_7", "logo_url_47")
            )
        )
    val TEST_GROUP_RESPONSE_5 =
        GroupResponse(
            "group_id_5",
            "group_name_5",
            listOf(
                ChannelResponse("channel-id-5-1", "channel_name_5_1", "logo_url_51"),
                ChannelResponse("channel-id-5-2", "channel_name_5_2", "logo_url_52"),
                ChannelResponse("channel-id-5-3", "channel_name_5_3", "logo_url_53"),
                ChannelResponse("channel-id-5-4", "channel_name_5_4", "logo_url_54"),
                ChannelResponse("channel-id-5-5", "channel_name_5_5", "logo_url_55"),
                ChannelResponse("channel-id-5-6", "channel_name_5_6", "logo_url_56"),
                ChannelResponse("channel-id-5-7", "channel_name_5_7", "logo_url_57"),
                ChannelResponse("channel-id-5-8", "channel_name_5_8", "logo_url_58"),
                ChannelResponse("channel-id-5-9", "channel_name_5_9", "logo_url_59")
            )
        )
    val TEST_GROUP_RESPONSES = arrayListOf(
        TEST_GROUP_RESPONSE_1,
        TEST_GROUP_RESPONSE_2,
        TEST_GROUP_RESPONSE_3,
        TEST_GROUP_RESPONSE_4,
        TEST_GROUP_RESPONSE_5
    )

    //  test program responses
    fun generateTestProgramResponses(id: Int, count: Int): List<ProgramResponse> {
        val list = mutableListOf<ProgramResponse>()
        for (i in 0 until count) {
            list.add(ProgramResponse("program-$id-$i", "Передача $id-$i"))
        }
        return list
    }

    //  TEST ENTITIES
    //  test group entities
    val TEST_GROUP_ENTITY_1 = GroupEntity("group_id_1", "group_name_1")
    val TEST_GROUP_ENTITY_2 = GroupEntity("group_id_2", "group_name_2")
    val TEST_GROUP_ENTITY_3 = GroupEntity("group_id_3", "group_name_3")
    val TEST_GROUP_ENTITY_4 = GroupEntity("group_id_4", "group_name_4")
    val TEST_GROUP_ENTITY_5 = GroupEntity("group_id_5", "group_name_5")
    val TEST_GROUP_ENTITIES = arrayListOf(
        TEST_GROUP_ENTITY_1,
        TEST_GROUP_ENTITY_2,
        TEST_GROUP_ENTITY_3,
        TEST_GROUP_ENTITY_4,
        TEST_GROUP_ENTITY_5
    )

    //  test channel entities
    //  for channel_1:
    val TEST_CHANNEL_ENTITY_1_1 = ChannelEntity("channel-id-1-1", "group_id_1", "channel_name_1_1", "logo_url_11")

    //  for channel_2:
    val TEST_CHANNEL_ENTITY_2_1 = ChannelEntity("channel-id-2-1", "group_id_2", "channel_name_2_1", "logo_url_21")
    val TEST_CHANNEL_ENTITY_2_2 = ChannelEntity("channel-id-2-2", "group_id_2", "channel_name_2_2", "logo_url_22")
    val TEST_CHANNEL_ENTITY_2_3 = ChannelEntity("channel-id-2-3", "group_id_2", "channel_name_2_3", "logo_url_23")
    val TEST_CHANNEL_ENTITIES_2 =
        arrayListOf(TEST_CHANNEL_ENTITY_2_1, TEST_CHANNEL_ENTITY_2_2, TEST_CHANNEL_ENTITY_2_3)

    //  for channel_3:
    val TEST_CHANNEL_ENTITY_3_1 = ChannelEntity("channel-id-3-1", "group_id_3", "channel_name_3_1", "logo_url_31")
    val TEST_CHANNEL_ENTITY_3_2 = ChannelEntity("channel-id-3-2", "group_id_3", "channel_name_3_2", "logo_url_32")
    val TEST_CHANNEL_ENTITY_3_3 = ChannelEntity("channel-id-3-3", "group_id_3", "channel_name_3_3", "logo_url_33")
    val TEST_CHANNEL_ENTITY_3_4 = ChannelEntity("channel-id-3-4", "group_id_3", "channel_name_3_4", "logo_url_34")
    val TEST_CHANNEL_ENTITY_3_5 = ChannelEntity("channel-id-3-5", "group_id_3", "channel_name_3_5", "logo_url_35")
    val TEST_CHANNEL_ENTITIES_3 = arrayListOf(
        TEST_CHANNEL_ENTITY_3_1,
        TEST_CHANNEL_ENTITY_3_2,
        TEST_CHANNEL_ENTITY_3_3,
        TEST_CHANNEL_ENTITY_3_4,
        TEST_CHANNEL_ENTITY_3_5
    )

    //  for channel_4:
    val TEST_CHANNEL_ENTITY_4_1 = ChannelEntity("channel-id-4-1", "group_id_4", "channel_name_4_1", "logo_url_41")
    val TEST_CHANNEL_ENTITY_4_2 = ChannelEntity("channel-id-4-2", "group_id_4", "channel_name_4_2", "logo_url_42")
    val TEST_CHANNEL_ENTITY_4_3 = ChannelEntity("channel-id-4-3", "group_id_4", "channel_name_4_3", "logo_url_43")
    val TEST_CHANNEL_ENTITY_4_4 = ChannelEntity("channel-id-4-4", "group_id_4", "channel_name_4_4", "logo_url_44")
    val TEST_CHANNEL_ENTITY_4_5 = ChannelEntity("channel-id-4-5", "group_id_4", "channel_name_4_5", "logo_url_45")
    val TEST_CHANNEL_ENTITY_4_6 = ChannelEntity("channel-id-4-6", "group_id_4", "channel_name_4_6", "logo_url_46")
    val TEST_CHANNEL_ENTITY_4_7 = ChannelEntity("channel-id-4-7", "group_id_4", "channel_name_4_7", "logo_url_47")
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
    val TEST_CHANNEL_ENTITY_5_1 = ChannelEntity("channel-id-5-1", "group_id_5", "channel_name_5_1", "logo_url_51")
    val TEST_CHANNEL_ENTITY_5_2 = ChannelEntity("channel-id-5-2", "group_id_5", "channel_name_5_2", "logo_url_52")
    val TEST_CHANNEL_ENTITY_5_3 = ChannelEntity("channel-id-5-3", "group_id_5", "channel_name_5_3", "logo_url_53")
    val TEST_CHANNEL_ENTITY_5_4 = ChannelEntity("channel-id-5-4", "group_id_5", "channel_name_5_4", "logo_url_54")
    val TEST_CHANNEL_ENTITY_5_5 = ChannelEntity("channel-id-5-5", "group_id_5", "channel_name_5_5", "logo_url_55")
    val TEST_CHANNEL_ENTITY_5_6 = ChannelEntity("channel-id-5-6", "group_id_5", "channel_name_5_6", "logo_url_56")
    val TEST_CHANNEL_ENTITY_5_7 = ChannelEntity("channel-id-5-7", "group_id_5", "channel_name_5_7", "logo_url_57")
    val TEST_CHANNEL_ENTITY_5_8 = ChannelEntity("channel-id-5-8", "group_id_5", "channel_name_5_8", "logo_url_58")
    val TEST_CHANNEL_ENTITY_5_9 = ChannelEntity("channel-id-5-9", "group_id_5", "channel_name_5_9", "logo_url_59")
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