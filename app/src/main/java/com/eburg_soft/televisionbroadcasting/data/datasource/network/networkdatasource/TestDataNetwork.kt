package com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource

import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ChannelResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.GroupResponse
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse

object TestDataNetwork {

    private val TEST_GROUP_RESPONSE_1 =
        GroupResponse(
            "group_1",
            "group_name_1",
            listOf(
                ChannelResponse("channel_101", "channel_name_101", "logo_url_101")
            )
        )
    private val TEST_GROUP_RESPONSE_2 = GroupResponse(
        "group_2",
        "group_name_2",
        listOf(
            ChannelResponse("channel_201", "channel_name_201", "logo_url_201"),
            ChannelResponse("channel_202", "channel_name_202", "logo_url_202"),
            ChannelResponse("channel_203", "channel_name_203", "logo_url_203")
        )
    )
    private val TEST_GROUP_RESPONSE_3 =
        GroupResponse(
            "group_3",
            "group_name_3",
            listOf(
                ChannelResponse("channel_301", "channel_name_301", "logo_url_301"),
                ChannelResponse("channel_302", "channel_name_302", "logo_url_302"),
                ChannelResponse("channel_303", "channel_name_303", "logo_url_303"),
                ChannelResponse("channel_304", "channel_name_304", "logo_url_304"),
                ChannelResponse("channel_305", "channel_name_305", "logo_url_305")
            )
        )
    private val TEST_GROUP_RESPONSE_4 =
        GroupResponse(
            "group_4",
            "group_name_4",
            listOf(
                ChannelResponse("channel_401", "channel_name_401", "logo_url_401"),
                ChannelResponse("channel_402", "channel_name_402", "logo_url_402"),
                ChannelResponse("channel_403", "channel_name_403", "logo_url_403"),
                ChannelResponse("channel_404", "channel_name_404", "logo_url_404"),
                ChannelResponse("channel_405", "channel_name_405", "logo_url_405"),
                ChannelResponse("channel_406", "channel_name_406", "logo_url_406"),
                ChannelResponse("channel_407", "channel_name_407", "logo_url_407")
            )
        )
    private val TEST_GROUP_RESPONSE_5 =
        GroupResponse(
            "group_5",
            "group_name_5",
            listOf(
                ChannelResponse("channel_501", "channel_name_501", "logo_url_501"),
                ChannelResponse("channel_502", "channel_name_502", "logo_url_502"),
                ChannelResponse("channel_503", "channel_name_503", "logo_url_503"),
                ChannelResponse("channel_504", "channel_name_504", "logo_url_504"),
                ChannelResponse("channel_505", "channel_name_505", "logo_url_505"),
                ChannelResponse("channel_506", "channel_name_506", "logo_url_506"),
                ChannelResponse("channel_507", "channel_name_507", "logo_url_507"),
                ChannelResponse("channel_508", "channel_name_508", "logo_url_508"),
                ChannelResponse("channel_509", "channel_name_509", "logo_url_509")
            )
        )
    val TEST_GROUP_RESPONSES = arrayListOf(
        TEST_GROUP_RESPONSE_1,
        TEST_GROUP_RESPONSE_2,
        TEST_GROUP_RESPONSE_3,
        TEST_GROUP_RESPONSE_4,
        TEST_GROUP_RESPONSE_5
    )

    fun generateTestProgramResponses(id: Int, count: Int): List<ProgramResponse> {
        val list = mutableListOf<ProgramResponse>()
        for (i in 0 until count) {
            list.add(ProgramResponse("program-$id-$i", "Передача $id-$i"))
        }
        return list
    }
}