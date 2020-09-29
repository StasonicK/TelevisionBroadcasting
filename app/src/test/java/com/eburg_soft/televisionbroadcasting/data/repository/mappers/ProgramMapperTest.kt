package com.eburg_soft.televisionbroadcasting.data.repository.mappers

import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.jupiter.api.*

class ProgramMapperTest {

    val mapper = ProgramMapper()

    /*
        Single ProgramResponse
        -   set channelId;
        -   map
    */
    @Test
    @Throws(Exception::class)
    fun convertSingleProgramResponse() {
        //  Arrange
        val response = TestUtil.generateTestProgramResponses(1, 1)
        val tempId = response[0].id
        var id = tempId.replace("program-", "")
        id = id.replaceAfter("-", "")
        id = id.replace("-", "")
        val channelId = "1"
        val expectedEntity = TestUtil.generateTestProgramEntities(id.toInt(), 1, channelId)

        // Act
        mapper.setChannelId(channelId)
        val resultEntity = mapper.map(response)

        // Assert
        Assertions.assertNotNull(resultEntity)
        Assertions.assertEquals(expectedEntity, resultEntity)
    }

    /*
        Many ProgramResponses
        -   map
    */
    @Test
    @Throws(Exception::class)
    fun convertManyProgramResponses() {
        //  Arrange
        val response = TestUtil.generateTestProgramResponses(1, 10)
        val tempId = response[0].id
        var id = tempId.replace("program-", "")
        id = id.replaceAfter("-", "")
        id = id.replace("-", "")
        val channelId = "1"
        val expectedEntity = TestUtil.generateTestProgramEntities(id.toInt(), 10, channelId)

        // Act
        mapper.setChannelId(channelId)
        val resultEntity = mapper.map(response)

        // Assert
        Assertions.assertNotNull(resultEntity)
        Assertions.assertEquals(expectedEntity, resultEntity)
    }
}