package com.eburg_soft.televisionbroadcasting.data.repository.mappers

import eburg_soft.televisionbroadcasting.utils.TestUtil
import org.junit.jupiter.api.*

class GroupMapperTest {

    /*
        Single GroupResponse
        -   map
    */
    @Test
    @Throws(Exception::class)
    fun convertSingleGroupResponse() {
        //  Arrange
        val response = arrayListOf(TestUtil.TEST_GROUP_RESPONSE_1)
        val expectedEntity = TestUtil.TEST_GROUP_ENTITY_1

        // Act
        val resultEntity = GroupMapper.map(response).keys.toList()[0]

        // Assert
        Assertions.assertNotNull(resultEntity)
        Assertions.assertEquals(expectedEntity, resultEntity)
    }

    /*
        Many GroupResponses
        -   map
    */
    @Test
    @Throws(Exception::class)
    fun convertManyGroupResponses() {
        //  Arrange
        val response = TestUtil.TEST_GROUP_RESPONSES
        val expectedEntity = TestUtil.TEST_GROUP_ENTITIES

        // Act
        val resultEntity = GroupMapper.map(response).keys.toList()

        // Assert
        Assertions.assertNotNull(resultEntity)
        Assertions.assertEquals(expectedEntity, resultEntity)
    }
}